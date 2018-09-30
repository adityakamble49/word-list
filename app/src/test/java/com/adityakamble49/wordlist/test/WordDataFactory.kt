package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.cache.entities.Word

/**
 * Word Data Factory
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object WordDataFactory {

    fun makeWord() = Word(0, DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString())

    fun makeWords(count: Int): List<Word> {
        val words = mutableListOf<Word>()
        repeat(count) {
            words.add(makeWord())
        }
        return words
    }
}