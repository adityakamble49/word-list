package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.adityakamble49.wordlist.cache.model.CachedWordList

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
}