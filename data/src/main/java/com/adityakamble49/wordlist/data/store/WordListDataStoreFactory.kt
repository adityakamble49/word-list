package com.adityakamble49.wordlist.data.store

import com.adityakamble49.wordlist.data.repository.WordListDataStore
import javax.inject.Inject

class WordListDataStoreFactory @Inject constructor(
        private val wordListCacheDataStore: WordListCacheDataStore) {

    fun getDataStore(): WordListDataStore = wordListCacheDataStore

    fun getCacheDataStore(): WordListCacheDataStore = wordListCacheDataStore
}