package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.cache.entities.Word

/**
 * Word Data Factory
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object WordDataFactory {

    fun makeWord(id: Int) = Word(id, DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString())

    fun makeWords(count: Int): MutableList<Word> {
        val words = mutableListOf<Word>()
        var counter = 1
        repeat(count) {
            words.add(makeWord(counter))
            counter++
        }
        return words
    }
}