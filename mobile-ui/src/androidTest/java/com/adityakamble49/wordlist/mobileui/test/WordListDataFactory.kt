package com.adityakamble49.wordlist.mobileui.test

import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.presentation.model.WordListView

object WordListDataFactory {

    fun makeWordList() = WordList(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())

    fun makeWordLists(count: Int): List<WordList> {
        val wordLists = mutableListOf<WordList>()
        repeat(count) {
            wordLists.add(makeWordList())
        }
        return wordLists
    }

    fun makeWordListView() = WordListView(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())
}