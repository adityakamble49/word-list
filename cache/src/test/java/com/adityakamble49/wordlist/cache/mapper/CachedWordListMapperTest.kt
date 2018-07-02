package com.adityakamble49.wordlist.cache.mapper

import com.adityakamble49.wordlist.cache.model.CachedWordList
import com.adityakamble49.wordlist.cache.test.WordListDataFactory
import com.adityakamble49.wordlist.data.model.WordListEntity
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CachedWordListMapperTest {

    private val cachedWordListMapper = CachedWordListMapper()

    @Test
    fun mapFromCacheTest() {
        val cache = WordListDataFactory.makeCachedWordList()
        val entity = cachedWordListMapper.mapFromCache(cache)
        assertEqualData(cache, entity)
    }

    @Test
    fun mapToCacheTest() {
        val entity = WordListDataFactory.makeWordListEntity()
        val cache = cachedWordListMapper.mapToCache(entity)
        assertEqualData(cache, entity)
    }

    private fun assertEqualData(cache: CachedWordList, entity: WordListEntity) {
        assertEquals(cache.id, entity.id)
        assertEquals(cache.hash, entity.hash)
        assertEquals(cache.marketplaceFilename, entity.marketplaceFilename)
        assertEquals(cache.name, entity.name)
        assertEquals(cache.lastWordId, entity.lastWordId)
    }
}