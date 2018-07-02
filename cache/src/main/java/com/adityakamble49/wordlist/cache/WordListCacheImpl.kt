package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.mapper.CachedWordListMapper
import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.data.repository.WordListCache
import io.reactivex.Single
import javax.inject.Inject

class WordListCacheImpl @Inject constructor(
        private val wordListDatabase: WordListDatabase,
        private val mapper: CachedWordListMapper) : WordListCache {

    override fun saveWordList(wordListEntity: WordListEntity): Single<Long> {
        return Single.just(wordListDatabase.cachedWordListDao()
                .insert(mapper.mapToCache(wordListEntity)))
    }
}