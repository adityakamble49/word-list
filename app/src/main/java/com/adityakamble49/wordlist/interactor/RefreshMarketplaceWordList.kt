package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.cache.db.MarketplaceWordListDao
import com.adityakamble49.wordlist.cache.db.WordListDao
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.model.WordListStatus
import com.adityakamble49.wordlist.remote.WordListService
import com.adityakamble49.wordlist.utils.Constants.RemoteUrls
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Refresh Marketplace Word List
 *
 * @author Aditya Kamble
 * @since 23/6/2018
 */
class RefreshMarketplaceWordList @Inject constructor(
        private val wordListService: WordListService,
        private val wordListDao: WordListDao,
        private val marketplaceWordListDao: MarketplaceWordListDao) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { e ->
            deleteIncompleteMarketplaceWordList()
            wordListService.getMarketplaceWordList(RemoteUrls.GITHUB_WORDLIST,
                    "")
                    .subscribe({
                        marketplaceWordListDao.insertList(updateWordListStatus(it))
                        e.onComplete()
                    }, { Timber.i(it) })

        }
    }

    private fun deleteIncompleteMarketplaceWordList() {
        // Get Local Word Lists
        val wordLists = wordListDao.getWordListsDirect()
        // Delete Incomplete status Lists from Marketplace and Local WordList
        val marketplaceWordListsLocal = marketplaceWordListDao.getMarketplaceWordListDirect()
        marketplaceWordListsLocal.forEach { marketplaceWordList ->
            if (marketplaceWordList.status == WordListStatus.DOWNLOADING.name) {
                marketplaceWordListDao.deleteList(marketplaceWordList)
                wordLists.forEach { wordList ->
                    if (wordList.marketplaceFilename == marketplaceWordList.name) {
                        wordListDao.delete(wordList)
                    }
                }
            }
        }
    }

    private fun updateWordListStatus(
            marketplaceWordListsFetched: List<MarketplaceWordList>): List<MarketplaceWordList> {

        val marketplaceWordLists = marketplaceWordListsFetched.toMutableList()
        // Keep Test Word List in Debug version only
        if (!BuildConfig.DEBUG) {
            marketplaceWordListsFetched.forEachIndexed { index, marketplaceWordList ->
                if (marketplaceWordList.name == "Test.json") {
                    marketplaceWordLists.removeAt(index)
                }
            }
        }

        // Reset Status to Not available
        marketplaceWordLists.forEach { it.status = WordListStatus.NOT_AVAILABLE.name }

        // Get Local Word Lists
        val wordLists = wordListDao.getWordListsDirect()

        // Update status of Fetched Marketplace wordlist by checking available offline word lists
        marketplaceWordLists.forEach { marketplaceWordList ->
            wordLists.forEach { wordList ->
                if (wordList.marketplaceFilename == marketplaceWordList.name) {
                    if (wordList.hash == marketplaceWordList.sha) {
                        marketplaceWordList.status = WordListStatus.AVAILABLE.name
                    } else {
                        marketplaceWordList.status = WordListStatus.UPDATE_AVAILABLE.name
                    }
                }
            }
        }
        return marketplaceWordLists
    }

    fun execute(observer: DisposableCompletableObserver) {
        val completable = buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(completable.subscribeWith(observer))
    }
}