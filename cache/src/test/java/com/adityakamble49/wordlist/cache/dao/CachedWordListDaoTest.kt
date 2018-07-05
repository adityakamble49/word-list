package com.adityakamble49.wordlist.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.test.DataFactory
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * CachedWordList Dao Test
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class CachedWordListDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertWordListReturnId() {
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

    @Test
    fun insertListOfWordListReturnsIds() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListIds = database.cachedWordListDao().insertList(listOf(cachedWordList))
        assertNotNull(wordListIds)
    }

    @Test
    fun updateWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = database.cachedWordListDao().insert(cachedWordList)

        // Change data and update list
        cachedWordList.lastWordId = DataFactory.randomInteger()
        cachedWordList.name = DataFactory.randomUUID()
        cachedWordList.hash = DataFactory.randomUUID()
        database.cachedWordListDao().update(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = database.cachedWordListDao().getWordListById(wordListId.toInt()).test()
        testObserver.assertValue(cachedWordList)
    }

    @Test
    fun updateLastWordIdForWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = database.cachedWordListDao().insert(cachedWordList)

        // Change lastWordId and update list
        val updatedLastWordId = DataFactory.randomInteger()
        cachedWordList.lastWordId = updatedLastWordId
        database.cachedWordListDao().update(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = database.cachedWordListDao().getWordListById(wordListId.toInt()).test()
        testObserver.assertValue({ it.lastWordId == updatedLastWordId })
    }

    @Test
    fun deleteWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = database.cachedWordListDao().insert(cachedWordList)

        // Delete WordList
        database.cachedWordListDao().delete(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = database.cachedWordListDao().getWordListById(wordListId.toInt()).test()
        testObserver.assertEmpty()
    }

    @Test
    fun getWordListTest() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        database.cachedWordListDao().insert(cachedWordList)
        val testObserver = database.cachedWordListDao().getWordList().test()
        testObserver.assertValue(listOf(cachedWordList))
    }

    @Test
    fun getWordListByIdReturns() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = database.cachedWordListDao().insert(cachedWordList)

        // Fetch WordList by Id
        val testObserver = database.cachedWordListDao().getWordListById(wordListId.toInt()).test()
        testObserver.assertValue(cachedWordList)
    }

    @Test
    fun getWordListByMarketplaceFileNameReturns() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        database.cachedWordListDao().insert(cachedWordList)

        // Fetch WordList by Id
        val testObserver = database.cachedWordListDao()
                .getWordListByMarketplaceFileName(cachedWordList.marketplaceFilename).test()
        testObserver.assertValue(cachedWordList)
    }
}