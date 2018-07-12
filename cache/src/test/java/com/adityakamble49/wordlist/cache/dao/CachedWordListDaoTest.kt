package com.adityakamble49.wordlist.cache.dao

import android.database.sqlite.SQLiteConstraintException
import com.adityakamble49.wordlist.cache.test.DataFactory
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * CachedWordList Dao Test
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class CachedWordListDaoTest : DaoTest() {

    private val dao = database.cachedWordListDao()

    @Test
    fun insertWordListReturnId() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = dao.insert(cachedWordList)
        assertNotNull(wordListId)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun insertWordListDuplicateNameThrowsException() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        dao.insert(cachedWordList)
        dao.insert(cachedWordList)
    }

    @Test
    fun insertListOfWordListReturnsIds() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListIds = dao.insertList(listOf(cachedWordList))
        assertNotNull(wordListIds)
    }

    @Test
    fun updateWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = dao.insert(cachedWordList)

        // Change data and update list
        cachedWordList.lastWordId = DataFactory.randomInteger()
        cachedWordList.name = DataFactory.randomString()
        cachedWordList.hash = DataFactory.randomString()
        dao.update(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = dao.getWordListById(wordListId.toInt()).test()
        testObserver.assertValue(cachedWordList)
    }

    @Test
    fun updateLastWordIdForWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = dao.insert(cachedWordList)

        // Change lastWordId and update list
        val updatedLastWordId = DataFactory.randomInteger()
        cachedWordList.lastWordId = updatedLastWordId
        dao.update(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = dao.getWordListById(wordListId.toInt()).test()
        testObserver.assertValue({ it.lastWordId == updatedLastWordId })
    }

    @Test
    fun deleteWordListCompletes() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = dao.insert(cachedWordList)

        // Delete WordList
        dao.delete(cachedWordList)

        // Fetch updated word list and assert lastWordId
        val testObserver = dao.getWordListById(wordListId.toInt()).test()
        testObserver.assertEmpty()
    }

    @Test
    fun getWordListTest() {
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        dao.insert(cachedWordList)
        val testObserver = dao.getWordList().test()
        testObserver.assertValue(listOf(cachedWordList))
    }

    @Test
    fun getWordListByIdReturns() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        val wordListId = dao.insert(cachedWordList)

        // Fetch WordList by Id
        val testObserver = dao.getWordListById(wordListId.toInt()).test()
        testObserver.assertValue(cachedWordList)
    }

    @Test
    fun getWordListByMarketplaceFileNameReturns() {
        // Insert Word List
        val cachedWordList = WordListDataFactory.makeCachedWordList()
        dao.insert(cachedWordList)

        // Fetch WordList by Id
        val testObserver = dao
                .getWordListByMarketplaceFileName(cachedWordList.marketplaceFilename).test()
        testObserver.assertValue(cachedWordList)
    }
}