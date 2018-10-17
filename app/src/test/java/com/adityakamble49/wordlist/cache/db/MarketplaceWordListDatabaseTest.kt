package com.adityakamble49.wordlist.cache.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import com.adityakamble49.wordlist.test.DataFactory
import com.adityakamble49.wordlist.test.MarketplaceWordListDataFactory
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Marketplace Word List Database Test
 *
 * @author Aditya Kamble
 * @since 15/10/2018
 */
@RunWith(RobolectricTestRunner::class)
class MarketplaceWordListDatabaseTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private val marketplaceWordListDao = database.marketplaceWordListDao()

    @Test
    fun insertMarketplaceWordListCompletes() {
        val marketplaceWordList = MarketplaceWordListDataFactory.makeMarketplaceWordList(1)
        val testResult = marketplaceWordListDao.insert(marketplaceWordList)
        assertThat(testResult, not(-1L))
    }

    @Test
    fun insertListOfMarketplaceWordListCompletes() {
        val listOfWordList = MarketplaceWordListDataFactory.makeListOfMarketplaceWordList(5)
        val testResult = marketplaceWordListDao.insertList(listOfWordList)
        assertThat(testResult, not(hasItem(-1L)))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun insertDuplicateMarketplaceWordListCompletes() {
        val marketplaceWordList = MarketplaceWordListDataFactory.makeMarketplaceWordList(1)
        val testResult = marketplaceWordListDao.insert(marketplaceWordList)
        assertThat(testResult, not(-1L))
        marketplaceWordListDao.insert(marketplaceWordList)
    }

    @Test
    fun updateWordListCompletes() {
        val listOfWordList = MarketplaceWordListDataFactory.makeListOfMarketplaceWordList(10)
        marketplaceWordListDao.insertList(listOfWordList)
        val marketplaceWordListToUpdate = listOfWordList[5]
        marketplaceWordListToUpdate.sha = DataFactory.randomString()
        val testResult = marketplaceWordListDao.update(marketplaceWordListToUpdate)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun deleteWordListCompletes() {
        val listOfWordList = MarketplaceWordListDataFactory.makeListOfMarketplaceWordList(10)
        marketplaceWordListDao.insertList(listOfWordList)
        val wordListToDelete = listOfWordList[5]
        val testResult = marketplaceWordListDao.delete(wordListToDelete)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun getWordListReturnsData() {
        val listOfMarketplaceWordList = MarketplaceWordListDataFactory.makeListOfMarketplaceWordList(
                10)
        marketplaceWordListDao.insertList(listOfMarketplaceWordList)
        val testObserver = marketplaceWordListDao.getMarketplaceWordList().test()
        testObserver.assertValue(listOfMarketplaceWordList)
    }

    @Test
    fun getWordListByIdReturnsData() {
        val listOfMarketplaceWordList = MarketplaceWordListDataFactory.makeListOfMarketplaceWordList(
                10)
        val wordListToFetch = listOfMarketplaceWordList[5]
        marketplaceWordListDao.insertList(listOfMarketplaceWordList)
        val testObserver = marketplaceWordListDao.getMarketplaceWordListByHash(wordListToFetch.sha)
                .test()
        testObserver.assertValue(wordListToFetch)
    }
}