package com.adityakamble49.wordlist.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.adityakamble49.wordlist.model.WordList

/**
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@Dao
interface WordListDao {

    @Insert
    fun insert(wordList: WordList): Long

    @Insert
    fun insertList(listOfWordList: List<WordList>): List<Long>

    @Query("SELECT * FROM word_lists")
    fun getWordLists(): LiveData<List<WordList>>

    @Query("SELECT  * FROM word_lists WHERE id= :id")
    fun getWordListById(id: Int): LiveData<WordList>
}