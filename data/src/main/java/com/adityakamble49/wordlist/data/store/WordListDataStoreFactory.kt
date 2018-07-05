package com.adityakamble49.wordlist.data.store

import com.adityakamble49.wordlist.data.repository.WordListDataStore
import javax.inject.Inject

/**
 * WordList DataStore Factory
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class WordListDataStoreFactory @Inject constructor(
        private val wordListCacheDataStore: WordListCacheDataStore) {

    fun getDataStore(): WordListDataStore = wordListCacheDataStore

    fun getCacheDataStore(): WordListCacheDataStore = wordListCacheDataStore
}