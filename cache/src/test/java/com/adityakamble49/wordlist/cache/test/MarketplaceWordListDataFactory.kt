package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.cache.model.CachedMarketplaceWordList
import com.adityakamble49.wordlist.cache.model.CachedMarketplaceWordListStatus
import com.adityakamble49.wordlist.cache.test.DataFactory.randomString

/**
 * Marketplace WordList Data Factory
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
object MarketplaceWordListDataFactory {

    fun makeCachedMarketplaceWordList() = CachedMarketplaceWordList(randomString(), randomString(),
            randomString(), CachedMarketplaceWordListStatus.NOT_AVAILABLE.name)

    fun makeCachedMarketplaceWordLists(count: Int): List<CachedMarketplaceWordList> {
        val list = mutableListOf<CachedMarketplaceWordList>()
        repeat(count) {
            list.add(makeCachedMarketplaceWordList())
        }
        return list
    }
}