package com.adityakamble49.wordlist.data.mapper

import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.data.test.WordListDataFactory
import com.adityakamble49.wordlist.domain.model.WordList
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WordListMapperTest {

    private val mapper = WordListMapper()

    @Test
    fun mapFromEntityTest() {
        val entity = WordListDataFactory.makeWordListEntity()
        val model = mapper.mapFromEntity(entity)
        assertDataEquals(entity, model)
    }

    @Test
    fun mapToEntityTest() {
        val model = WordListDataFactory.makeWordList()
        val entity = mapper.mapToEntity(model)
        assertDataEquals(entity, model)
    }

    private fun assertDataEquals(entity: WordListEntity, model: WordList) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.hash, model.hash)
        assertEquals(entity.marketplaceFilename, model.marketplaceFilename)
        assertEquals(entity.name, model.name)
        assertEquals(entity.lastWordId, model.lastWordId)
    }
}