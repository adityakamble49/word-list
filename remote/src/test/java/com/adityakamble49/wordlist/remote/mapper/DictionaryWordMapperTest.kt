package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.remote.model.DictionaryWord
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * DictionaryWord Mapper Test
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
@RunWith(JUnit4::class)
class DictionaryWordMapperTest {

    private val mapper = DictionaryWordMapper()

    @Test
    fun mapFromModelTest() {
        val model = RemoteDataFactory.makeDictionaryWord()
        val entity = mapper.mapFromModel(model)
        assertEqualData(model, entity)
    }

    @Test
    fun mapFromModelTestWithNull() {
        val model = RemoteDataFactory.makeDictionaryWord(true)
        val entity = mapper.mapFromModel(model)
        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: DictionaryWord, entity: DictionaryWordEntity) {
        if (model.type == null) {
            assertEquals("", entity.type)
        } else {
            assertEquals(model.type, entity.type)
        }
        if (model.definition == null) {
            assertEquals("", entity.definition)
        } else {
            assertEquals(model.definition, entity.definition)
        }
        if (model.example == null) {
            assertEquals("", entity.example)
        } else {
            assertEquals(model.example, entity.example)
        }
    }
}