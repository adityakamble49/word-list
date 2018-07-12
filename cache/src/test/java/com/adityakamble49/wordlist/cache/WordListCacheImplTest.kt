package com.adityakamble49.wordlist.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.exceptions.WordListNameExistException
import com.adityakamble49.wordlist.cache.mapper.CachedWordListMapper
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import com.adityakamble49.wordlist.cache.test.sortWordListEntity
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class WordListCacheImplTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val mapper = CachedWordListMapper()
    private val cache = WordListCacheImpl(database, mapper)

    @Test
    fun saveWordListCompletes() {
        val wordList = WordListDataFactory.makeWordListEntity()
        val testObserver = cache.saveWordList(wordList).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveWordListDuplicateNameThrowException() {
        val wordList = WordListDataFactory.makeWordListEntity()
        cache.saveWordList(wordList).test().assertComplete()
        cache.saveWordList(wordList).test().assertError(WordListNameExistException::class.java)
    }

    @Test
    fun getWordListReturnsData() {
        val wordList = WordListDataFactory.makeWordListEntity()
        cache.saveWordList(wordList).test()
        val testObserver = cache.getWordLists().test()
        testObserver.assertValues(listOf(wordList))
    }

    @Test
    fun getWordListObservesData() {
        val wordList1 = WordListDataFactory.makeWordListEntity()
        cache.saveWordList(wordList1).test()
        val testObserver = cache.getWordLists().test()
        testObserver.assertValues(listOf(wordList1))
        val wordList2 = WordListDataFactory.makeWordListEntity()
        cache.saveWordList(wordList2).test()
        assertEquals(listOf(wordList1, wordList2).sortWordListEntity(), testObserver.values()[1].sortWordListEntity())
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}