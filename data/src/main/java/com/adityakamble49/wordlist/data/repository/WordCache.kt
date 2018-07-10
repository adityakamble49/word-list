package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.WordEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Word Cache Interface
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */
interface WordCache {

    fun saveWord(wordEntity: WordEntity): Single<Long>

    fun saveWords(wordEntityList: List<WordEntity>): Flowable<List<Long>>

    fun updateWord(wordEntity: WordEntity): Completable

    fun getWords(): Flowable<List<WordEntity>>

    fun getWordsInList(listId: Int): Flowable<List<WordEntity>>

    fun getWordById(id: Int): Single<WordEntity>

    fun deleteWordsInList(listId: Int): Completable
}