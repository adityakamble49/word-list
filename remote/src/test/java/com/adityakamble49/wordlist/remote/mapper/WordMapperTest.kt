package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.WordEntity
import com.adityakamble49.wordlist.remote.model.Word
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Word Mapper Test
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class WordMapperTest {

    private val mapper = WordMapper()

    @Test
    fun mapFromModelTest() {
        val model = RemoteDataFactory.makeWord()
        val entity = mapper.mapFromModel(model)
        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: Word, entity: WordEntity) {
        assertEquals(model.name, entity.name)
        assertEquals(model.type, entity.type)
        assertEquals(model.pronunciation, entity.pronunciation)
        assertEquals(model.information, entity.information)
        assertEquals(model.mnemonic, entity.mnemonic)
    }
}