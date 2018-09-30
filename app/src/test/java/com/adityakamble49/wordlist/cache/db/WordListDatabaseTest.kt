package com.adityakamble49.wordlist.cache.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
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
        val wordList = WordListDataFactory.makeWordList()
        val testResult = database.wordListDao().insert(wordList)
        print(testResult)
        assertThat(testResult, not(-1L))
    }

    @Test
    fun insertListOfWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(2)
        val testResult = database.wordListDao().insertList(listOfWordList)
        print(testResult)
        assertThat(testResult, not(hasItem(-1L)))
    }
}