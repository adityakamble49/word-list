package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.model.CachedWordList
import io.reactivex.Flowable

/**
 * Cached Word List Dao
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
@Dao
interface CachedWordListDao {

    @Insert
    fun insert(wordList: CachedWordList): Long

    @Insert
    fun insertList(listOfWordList: List<CachedWordList>): List<Long>

    @Update
    fun update(wordList: CachedWordList)

    @Query("UPDATE word_lists SET lastWordId= :lastWordId WHERE id= :wordListId")
    fun updateLastWordIdForWordList(wordListId: Int, lastWordId: Int)

    @Delete
    fun delete(wordList: CachedWordList)

    @Query("SELECT * FROM word_lists")
    fun getWordList(): Flowable<List<CachedWordList>>

    @Query("SELECT  * FROM word_lists WHERE id= :id")
    fun getWordListById(id: Int): Flowable<CachedWordList>

    @Query("SELECT  * FROM word_lists WHERE marketplaceFilename= :fileName")
    fun getWordListByMarketplaceFileName(fileName: String): Flowable<CachedWordList?>
}