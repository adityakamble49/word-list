package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.cache.entities.WordListWordJoin
import io.reactivex.Flowable

/**
 * Word List Word Join DAO
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Dao
interface WordListWordJoinDao {

    @Insert
    fun insert(wordListWordJoin: WordListWordJoin)

    @Insert
    fun insertList(listOfWordListWordJoin: List<WordListWordJoin>)

    @Delete
    fun delete(wordListWordJoin: WordListWordJoin): Int

    @Query("SELECT * FROM words INNER JOIN word_list_words_join ON words.id = word_list_words_join.wordId WHERE word_list_words_join.wordListId= :wordListId")
    fun getWordsInWordList(wordListId: Int): Flowable<List<Word>>
}