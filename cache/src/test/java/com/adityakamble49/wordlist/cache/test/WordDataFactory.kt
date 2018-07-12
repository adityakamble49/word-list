package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.cache.model.CachedWord

/**
 * Word Data Factory
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
object WordDataFactory {

    fun makeCachedWord() = CachedWord(DataFactory.randomInteger(), DataFactory.randomInteger(),
            DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString())

    fun makeCachedWords(count: Int): List<CachedWord> {
        val words = mutableListOf<CachedWord>()
        repeat(count) {
            words.add(makeCachedWord())
        }
        return words
    }
}