package com.adityakamble49.wordlist.data.test

import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.domain.model.WordList

object WordListDataFactory {

    fun makeWordList() = WordList(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())

    fun makeWordListEntityList(count: Int): List<WordListEntity> {
        val wordListEntityList = mutableListOf<WordListEntity>()
        repeat(count) {
            wordListEntityList.add(makeWordListEntity())
        }
        return wordListEntityList
    }

    fun makeWordListEntity() = WordListEntity(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())
}