package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.cache.exceptions.WordListNameExistException
import com.adityakamble49.wordlist.cache.mapper.CachedWordListMapper
import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.data.repository.WordListCache
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WordListCacheImpl @Inject constructor(
        private val wordListDatabase: WordListDatabase,
        private val mapper: CachedWordListMapper) : WordListCache {

    override fun saveWordList(wordListEntity: WordListEntity): Single<Long> {
        return wordListDatabase.cachedWordListDao().getWordList()
                .map {
                    it.forEach {
                        if (it.name == wordListEntity.name) {
                            return@map true
                        }
                    }
                    return@map false
                }
                .first(false)
                .map {
                    if (!it) {
                        return@map wordListDatabase.cachedWordListDao()
                                .insert(mapper.mapToCache(wordListEntity))
                    } else {
                        throw WordListNameExistException("${wordListEntity.name} already exists")
                    }
                }
    }

    override fun getWordLists(): Observable<List<WordListEntity>> {
        return wordListDatabase.cachedWordListDao().getWordList()
                .map {
                    it.map { mapper.mapFromCache(it) }
                }
                .toObservable()
    }
}