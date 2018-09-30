package com.adityakamble49.wordlist.cache.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.cache.entities.WordListWordJoin
import com.adityakamble49.wordlist.test.WordDataFactory
import com.adityakamble49.wordlist.test.WordListDataFactory
import org.hamcrest.Matchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Word List Database Test
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@RunWith(RobolectricTestRunner::class)
class WordListDatabaseTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Test
    fun insertWordListCompletes() {
        val wordList = WordListDataFactory.makeWordList(1)
        val testResult = database.wordListDao().insert(wordList)
        assertThat(testResult, not(-1L))
    }

    @Test
    fun insertListOfWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        val testResult = database.wordListDao().insertList(listOfWordList)
        assertThat(testResult, not(hasItem(-1L)))
    }

    @Test
    fun getWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        database.wordListDao().insertList(listOfWordList)
        val testObserver = database.wordListDao().getWordList().test()
        testObserver.assertValue(listOfWordList)
    }

    @Test
    fun insertWordsInWordList() {
        val words = WordDataFactory.makeWords(10)
        val wordList = WordListDataFactory.makeWordList(1)
        val insertedWordIds = database.wordDao().insertWords(words)
        val insertedWordListId = database.wordListDao().insert(wordList)
        val listOfWordListWordJoin = mutableListOf<WordListWordJoin>()
        words.forEachIndexed { index, word ->
            listOfWordListWordJoin.add(WordListWordJoin(insertedWordListId.toInt(),
                    insertedWordIds[index].toInt()))
        }
        database.wordListWordJoinDao().insertList(listOfWordListWordJoin)

        val testObserver = database.wordListWordJoinDao()
                .getWordsInWordList(insertedWordListId.toInt()).test()
        testObserver.assertValue(words)
    }
}