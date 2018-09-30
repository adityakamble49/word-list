package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.cache.entities.WordList

/**
 * WordList Data Factory
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object WordListDataFactory {

    fun makeWordList(id: Int) = WordList(id, DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString())

    fun makeListOfWordList(count: Int): List<WordList> {
        val listOfWordList = mutableListOf<WordList>()
        var counter = 1
        repeat(count) {
            listOfWordList.add(makeWordList(counter))
            counter++
        }
        return listOfWordList
    }
}