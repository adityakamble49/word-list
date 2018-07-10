package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.WordListEntity
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * WordList Cache
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
interface WordListCache {

    fun saveWordList(wordListEntity: WordListEntity): Single<Long>

    fun getWordLists(): Flowable<List<WordListEntity>>
}