package com.adityakamble49.wordlist.presentation.test

import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.presentation.model.WordListView

object WordListDataFactory {

    fun makeWordList() = WordList(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())

    fun makeWordListView() = WordListView(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())
}