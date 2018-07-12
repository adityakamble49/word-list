package com.adityakamble49.wordlist.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import org.junit.After
import org.junit.Rule
import org.robolectric.RuntimeEnvironment

/**
 * Base Dao Test
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
abstract class DaoTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    protected val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDatabase() {
        database.close()
    }
}