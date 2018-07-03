package com.adityakamble49.wordlist.data.store

import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.data.repository.WordListCache
import com.adityakamble49.wordlist.data.repository.WordListDataStore
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WordListCacheDataStore @Inject constructor(
        private val wordListCache: WordListCache) : WordListDataStore {

    override fun saveWordList(wordListEntity: WordListEntity): Single<Long> {
        return wordListCache.saveWordList(wordListEntity)
    }

    override fun getWordLists(): Observable<List<WordListEntity>> {
        return wordListCache.getWordLists()
    }
}