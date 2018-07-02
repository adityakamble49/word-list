package com.adityakamble49.wordlist.data.test

import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.domain.model.WordList

object WordListDataFactory {

    fun makeWordList() = WordList(DataFactory.randomNumber(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomNumber())

    fun makeWordListEntity() = WordListEntity(DataFactory.randomNumber(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomNumber())
}