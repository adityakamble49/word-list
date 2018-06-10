package com.adityakamble49.wordlist.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
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

    @Query("SELECT * FROM word_lists")
    fun getWordListsDirect(): List<WordList>

    @Query("SELECT  * FROM word_lists WHERE id= :id")
    fun getWordListById(id: Int): LiveData<WordList>

    @Query("SELECT  * FROM word_lists WHERE id= :id")
    fun getWordListByIdDirect(id: Int): WordList

    @Update
    fun update(wordList: WordList)

    @Query("UPDATE word_lists SET lastWordId= :lastWordId WHERE id= :wordListId")
    fun updateLastWordIdForWordList(wordListId: Int, lastWordId: Int)
}