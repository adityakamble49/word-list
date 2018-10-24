package com.adityakamble49.wordlist.repository

import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Word Repository
 *
 * @author Aditya Kamble
 * @since 24/10/2018
 */
class WordRepository @Inject constructor(
        private val wordListDatabase: WordListDatabase,
        private val wordListService: WordListService) {

    private val wordDao = wordListDatabase.wordDao()

    fun getWordInfo(wordName: String): Single<Word> {
        return wordDao.getWordByName(wordName)
                .switchIfEmpty(getWordInfoRemote(wordName))

    }

    private fun getWordInfoRemote(wordName: String): Single<Word> {
        return wordListService.getWordInfo(RemoteUrls.getWordKeyVal(wordName),
                RemoteUrls.WORD_INFO_API_KEY)
                .flatMap { t -> Single.just(t[0]) }
    }
}