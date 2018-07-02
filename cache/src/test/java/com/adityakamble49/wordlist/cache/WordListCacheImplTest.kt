package com.adityakamble49.wordlist.cache

import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.mapper.CachedWordListMapper
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class WordListCacheImplTest {

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
}