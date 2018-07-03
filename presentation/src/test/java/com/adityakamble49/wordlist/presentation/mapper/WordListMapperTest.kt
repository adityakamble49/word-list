package com.adityakamble49.wordlist.presentation.mapper

import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.presentation.model.WordListView
import com.adityakamble49.wordlist.presentation.test.WordListDataFactory
import junit.framework.TestCase
import org.junit.Test


class WordListMapperTest {
    private val mapper = WordListMapper()
    @Test
    fun mapToViewTest() {
        val model = WordListDataFactory.makeWordList()
        val modelView = mapper.mapToView(model)
        assertDataEquals(modelView, model)
    }

    private fun assertDataEquals(modelView: WordListView, model: WordList) {
        TestCase.assertEquals(modelView.id, model.id)
        TestCase.assertEquals(modelView.hash, model.hash)
        TestCase.assertEquals(modelView.marketplaceFilename, model.marketplaceFilename)
        TestCase.assertEquals(modelView.name, model.name)
        TestCase.assertEquals(modelView.lastWordId, model.lastWordId)
    }
}