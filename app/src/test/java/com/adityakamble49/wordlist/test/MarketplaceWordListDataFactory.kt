package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.cache.entities.MarketplaceWordList
import com.adityakamble49.wordlist.test.DataFactory.randomInteger
import com.adityakamble49.wordlist.test.DataFactory.randomString

/**
 * Marketplace WordList Data Factory
 *
 * @author Aditya Kamble
 * @since 15/10/2018
 */
object MarketplaceWordListDataFactory {

    fun makeMarketplaceWordList(id: Int) = MarketplaceWordList(randomString(), randomString(),
            randomInteger(), randomString(), randomString(), randomString(), randomString(),
            randomString())

    fun makeListOfWordList(count: Int): List<MarketplaceWordList> {
        val listOfWordList = mutableListOf<MarketplaceWordList>()
        var counter = 1
        repeat(count) {
            listOfWordList.add(makeMarketplaceWordList(counter))
            counter++
        }
        return listOfWordList
    }
}