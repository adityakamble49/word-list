package com.adityakamble49.wordlist.remote.test

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import com.adityakamble49.wordlist.data.model.MarketplaceWordListStatus
import com.adityakamble49.wordlist.remote.model.DictionaryWord
import com.adityakamble49.wordlist.remote.model.Links
import com.adityakamble49.wordlist.remote.model.MarketplaceWordList
import com.adityakamble49.wordlist.remote.model.Word

/**
 * Remote Data Factory
 * It provides sample data for testing
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
object RemoteDataFactory {

    fun makeMarketplaceWordList(): MarketplaceWordList {
        return MarketplaceWordList(DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomInteger(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), makeLinks())
    }

    private fun makeLinks(): Links {
        return Links(DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString())
    }

    fun makeListOfMarketplaceWordList(count: Int): MutableList<MarketplaceWordList> {
        val listOfMarketplaceWordList = mutableListOf<MarketplaceWordList>()
        repeat(count) {
            listOfMarketplaceWordList.add(makeMarketplaceWordList())
        }
        return listOfMarketplaceWordList
    }

    fun makeMarketplaceWordListEntity(): MarketplaceWordListEntity {
        return MarketplaceWordListEntity(DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), MarketplaceWordListStatus.NOT_AVAILABLE)
    }

    fun makeWord(): Word {
        return Word(DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeWords(count: Int): List<Word> {
        val words = mutableListOf<Word>()
        repeat(count) {
            words.add(makeWord())
        }
        return words
    }

    fun makeDictionaryWord(enableNull: Boolean = false): DictionaryWord {
        return if (enableNull) {
            DictionaryWord(null, DataFactory.randomString(),
                    null)
        } else {
            DictionaryWord(DataFactory.randomString(), DataFactory.randomString(),
                    DataFactory.randomString())
        }
    }

    fun makeDictionaryWordEntity(): DictionaryWordEntity {
        return DictionaryWordEntity(DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString())
    }

    fun makeDictionaryWords(count: Int): List<DictionaryWord> {
        val dictionaryWords = mutableListOf<DictionaryWord>()
        repeat(count) {
            dictionaryWords.add(makeDictionaryWord(count % 3 == 0))
        }
        return dictionaryWords
    }
}