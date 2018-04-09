package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordListJoin

/**
 * Word List Join Dao
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@Dao
interface WordListJoinDao {

    @Insert
    fun insert(wordListJoin: WordListJoin)

    @Insert
    fun insertList(wordListJoinList: List<WordListJoin>)

    @Query("SELECT * FROM words INNER JOIN word_list_join ON words.id == word_list_join.wordId WHERE word_list_join.wordListId= :wordListId")
    fun getWordsForWordList(wordListId: Int): List<Word>
}