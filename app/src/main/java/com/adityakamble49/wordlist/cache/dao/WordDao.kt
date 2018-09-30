package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.entities.Word
import io.reactivex.Flowable

/**
 * Word DAO
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Dao
interface WordDao {

    @Insert
    fun insertWord(word: Word): Long

    @Insert
    fun insertWords(wordList: List<Word>): List<Long>

    @Update
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT * FROM words")
    fun getWords(): Flowable<List<Word>>

    @Query("SELECT * FROM words WHERE id= :id")
    fun getWordById(id: Int): Flowable<Word>
}