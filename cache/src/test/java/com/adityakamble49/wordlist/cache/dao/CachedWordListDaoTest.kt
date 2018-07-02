package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedWordListDaoTest {

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertWordList() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = database.cachedWordListDao().insert(cachedWordList)
        assertNotNull(wordListId)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun insertWordListDuplicateNameThrowsException() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        database.cachedWordListDao().insert(cachedWordList)
        database.cachedWordListDao().insert(cachedWordList)
    }
}