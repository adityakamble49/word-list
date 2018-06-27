package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.MarketplaceWordListDao
import com.adityakamble49.wordlist.cache.db.WordDao
import com.adityakamble49.wordlist.cache.db.WordListDao
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListStatus
import com.adityakamble49.wordlist.remote.WordListService
import com.adityakamble49.wordlist.utils.Constants
import com.adityakamble49.wordlist.utils.getWordListNameFromFileName
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Download Word List from Marketplace
 *
 * @author Aditya Kamble
 * @since 23/6/2018
 */
class DownloadWordListFromMarketplace @Inject constructor(
        private val wordListService: WordListService,
        private val wordDao: WordDao,
        private val wordListDao: WordListDao,
        private val marketplaceWordListDao: MarketplaceWordListDao) {

    private fun buildUseCaseObservable(marketplaceWordList: MarketplaceWordList): Completable {
        return Completable.create { e ->
            if (isWordListVersionPresent(marketplaceWordList)) {
                e.onComplete()
            } else {
                wordListService.getWordListFromMarketplace(marketplaceWordList.downloadUrl,
                        "token ${Constants.RemoteUrls.GITHUB_AUTH_TOKEN}")
                        .subscribe({
                            insertWordListToDatabase(marketplaceWordList, it)
                            e.onComplete()
                        }, { Timber.i(it) })
            }
        }
    }

    private fun isWordListVersionPresent(marketplaceWordList: MarketplaceWordList): Boolean {
        wordListDao.getWordListsDirect().forEach {
            if (it.marketplaceFilename == marketplaceWordList.name) {
                if (it.hash == marketplaceWordList.sha) {
                    return true
                }
            }
        }
        return false
    }

    private fun insertWordListToDatabase(marketplaceWordList: MarketplaceWordList,
                                         words: List<Word>) {
        val wordList = WordList(0,
                marketplaceWordList.sha,
                marketplaceWordList.name,
                getWordListNameFromFileName(marketplaceWordList.name),
                0)
        val wordListId = wordListDao.insert(wordList)
        words.forEach { it.listId = wordListId.toInt() }
        val wordsIds = wordDao.insertWords(words)
        val fetchedWordList = wordListDao.getWordListByIdDirect(wordListId.toInt())
        fetchedWordList.lastWordId = wordsIds[0].toInt()
        wordListDao.update(fetchedWordList)
        marketplaceWordList.status = WordListStatus.AVAILABLE.name
        marketplaceWordListDao.updateList(marketplaceWordList)
    }

    fun execute(marketplaceWordList: MarketplaceWordList): Completable {
        return buildUseCaseObservable(marketplaceWordList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}