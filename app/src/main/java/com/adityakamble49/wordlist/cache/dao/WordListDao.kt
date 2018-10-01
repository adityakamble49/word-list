package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.entities.WordList
import io.reactivex.Flowable

/**
 * Word List Dao
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Dao
interface WordListDao {

    @Insert
    fun insert(wordList: WordList): Long

    @Insert
    fun insertList(listOfWordList: List<WordList>): List<Long>

    @Update
    fun update(wordList: WordList): Int

    @Delete
    fun delete(wordList: WordList): Int

    @Query("SELECT * FROM word_lists")
    fun getWordList(): Flowable<List<WordList>>

    @Query("SELECT  * FROM word_lists WHERE id= :id")
    fun getWordListById(id: Int): Flowable<WordList>

    @Query("SELECT  * FROM word_lists WHERE marketplaceFilename= :fileName")
    fun getWordListByMarketplaceFileName(fileName: String): Flowable<WordList?>
}