package com.adityakamble49.wordlist.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.adityakamble49.wordlist.model.Word

/**
 * Word DAO
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Dao
interface WordDao {

    @Insert
    fun insertWord(word: Word): Long

    @Insert
    fun insertWords(wordList: List<Word>): List<Long>

    @Update
    fun updateWord(word: Word)

    @Query("SELECT * FROM words")
    fun getWordList(): LiveData<List<Word>>

    @Query("SELECT * FROM words WHERE listId= :listId")
    fun getWordList(listId: Int): LiveData<List<Word>>

    @Query("SELECT * FROM words WHERE listId= :listId")
    fun getWordListDirect(listId: Int): List<Word>

    @Query("SELECT * FROM words WHERE id= :id")
    fun getWordById(id: Int): LiveData<Word>

    @Query("SELECT * FROM words")
    fun getAllWords(): List<Word>

    @Query("DELETE FROM words WHERE listId= :listId")
    fun deleteWordsOf(listId: Int)
}