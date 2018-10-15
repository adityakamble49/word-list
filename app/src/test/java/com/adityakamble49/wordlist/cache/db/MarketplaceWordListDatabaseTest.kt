package com.adityakamble49.wordlist.cache.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.test.MarketplaceWordListDataFactory
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
}