package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.model.CachedWord
import io.reactivex.Flowable

/**
 * Cached Word DAO
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@Dao
interface CachedWordDao {

    @Insert
    fun insertWord(word: CachedWord): Long

    @Insert
    fun insertWords(wordList: List<CachedWord>): List<Long>

    @Update
    fun updateWord(word: CachedWord)

    @Delete
    fun deleteWord(word: CachedWord)

    @Query("DELETE FROM words WHERE listId= :listId")
    fun deleteWordsByListId(listId: Int)

    @Query("SELECT * FROM words")
    fun getWords(): Flowable<List<CachedWord>>

    @Query("SELECT * FROM words WHERE id= :id")
    fun getWordById(id: Int): Flowable<CachedWord>

    @Query("SELECT * FROM words WHERE listId= :listId")
    fun getWordsByListId(listId: Int): Flowable<List<CachedWord>>
}