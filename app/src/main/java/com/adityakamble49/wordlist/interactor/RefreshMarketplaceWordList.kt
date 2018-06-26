package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.MarketplaceWordListDao
import com.adityakamble49.wordlist.cache.db.WordListDao
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.model.WordListStatus
import com.adityakamble49.wordlist.remote.WordListService
import com.adityakamble49.wordlist.utils.Constants.RemoteUrls
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
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
        private val marketplaceWordListDao: MarketplaceWordListDao) {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { e ->
            wordListService.getMarketplaceWordList(RemoteUrls.GITHUB_WORDLIST,
                    "token ${RemoteUrls.GITHUB_AUTH_TOKEN}")
                    .subscribe({
                        marketplaceWordListDao.insertList(updateWordListStatus(it))
                        e.onComplete()
                    }, { Timber.i(it) })

        }
    }

    private fun updateWordListStatus(
            marketplaceWordLists: List<MarketplaceWordList>): List<MarketplaceWordList> {
        marketplaceWordLists.forEach { it.status = WordListStatus.NOT_AVAILABLE.name }
        val wordLists = wordListDao.getWordListsDirect()
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

    fun execute(): Completable {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}