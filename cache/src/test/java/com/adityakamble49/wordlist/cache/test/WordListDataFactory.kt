package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.cache.model.CachedWordList
import com.adityakamble49.wordlist.data.model.WordListEntity

/**
 * WordList Data Factory
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
object WordListDataFactory {

    fun makeCachedWordList() = CachedWordList(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())

    fun makeWordListEntity() = WordListEntity(DataFactory.randomInteger(), DataFactory.randomUUID(),
            DataFactory.randomUUID(), DataFactory.randomUUID(), DataFactory.randomInteger())
}