package com.adityakamble49.wordlist.data.store

import com.adityakamble49.wordlist.data.repository.WordListCache
import com.adityakamble49.wordlist.data.test.DataFactory
import com.adityakamble49.wordlist.data.test.WordListDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class WordListCacheDataStoreTest {

    @Mock private lateinit var cache: WordListCache
    private lateinit var store: WordListCacheDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        store = WordListCacheDataStore(cache)
    }

    @Test
    fun saveWordListCompletes() {
        stubWordListCacheSaveWordList(Single.just(DataFactory.randomLong()))
        val testObserver = store.saveWordList(WordListDataFactory.makeWordListEntity()).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveWordListReturnsData() {
        val wordListId = DataFactory.randomLong()
        stubWordListCacheSaveWordList(Single.just(wordListId))
        val testObserver = store.saveWordList(WordListDataFactory.makeWordListEntity()).test()
        testObserver.assertValue(wordListId)
    }

    @Test
    fun saveWordListCallsCacheSource() {
        stubWordListCacheSaveWordList(Single.just(DataFactory.randomLong()))
        val wordListEntity = WordListDataFactory.makeWordListEntity()
        store.saveWordList(wordListEntity).test()
        verify(cache).saveWordList(wordListEntity)
    }

    private fun stubWordListCacheSaveWordList(single: Single<Long>) {
        whenever(cache.saveWordList(any())).thenReturn(single)
    }
}