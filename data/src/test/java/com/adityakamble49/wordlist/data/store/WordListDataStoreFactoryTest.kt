package com.adityakamble49.wordlist.data.store

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WordListDataStoreFactoryTest {

    @Mock private lateinit var cacheStore: WordListCacheDataStore
    private lateinit var factory: WordListDataStoreFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        factory = WordListDataStoreFactory(cacheStore)
    }

    @Test
    fun getDataStoreReturnsCacheStore() {
        assertEquals(cacheStore, factory.getDataStore())
    }
}