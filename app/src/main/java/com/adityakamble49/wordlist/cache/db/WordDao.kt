package com.adityakamble49.wordlist.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
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
    fun insertWord(word: Word)

    @Insert
    fun insertWords(wordList: List<Word>)

    @Query("SELECT * FROM words")
    fun getWordList(): LiveData<List<Word>>

    @Query("SELECT * FROM words WHERE listType= :listType")
    fun getWordList(listType: Int): LiveData<List<Word>>
}