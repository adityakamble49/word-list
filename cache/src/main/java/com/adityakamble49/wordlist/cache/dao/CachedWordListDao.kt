package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
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

    @Query("SELECT * FROM word_lists")
    fun getWordList(): Flowable<List<CachedWordList>>
}