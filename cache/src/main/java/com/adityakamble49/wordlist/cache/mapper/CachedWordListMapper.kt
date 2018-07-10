package com.adityakamble49.wordlist.cache.mapper

import com.adityakamble49.wordlist.cache.model.CachedWordList
import com.adityakamble49.wordlist.data.model.WordListEntity
import javax.inject.Inject

class CachedWordListMapper @Inject constructor() : CacheMapper<CachedWordList, WordListEntity> {

    override fun mapFromCache(cache: CachedWordList): WordListEntity {
        return WordListEntity(cache.id, cache.hash, cache.marketplaceFilename, cache.name,
                cache.lastWordId)
    }

    override fun mapToCache(entity: WordListEntity): CachedWordList {
        return CachedWordList(entity.id, entity.hash, entity.marketplaceFilename, entity.name,
                entity.lastWordId)
    }
}