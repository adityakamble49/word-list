package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.model.RelatedWordAdjective
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.model.RelatedWordDescribe
import com.adityakamble49.wordlist.model.RelatedWordTriggered

/**
 * Remote Data Factory
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
object RemoteDataFactory {

    fun makeRelatedWordBasic() = RelatedWordBasic(DataFactory.randomString(),
            DataFactory.randomInteger(),
            listOf(DataFactory.randomString(), DataFactory.randomString()))

    fun makeListOfRelatedWordBasic(count: Int): List<RelatedWordBasic> {
        val list = mutableListOf<RelatedWordBasic>()
        repeat(count) {
            list.add(makeRelatedWordBasic())
        }
        return list
    }

    fun makeRelatedWordDescribe() = RelatedWordDescribe(DataFactory.randomString(),
            DataFactory.randomInteger())

    fun makeListOfRelatedWordDescribe(count: Int): List<RelatedWordDescribe> {
        val list = mutableListOf<RelatedWordDescribe>()
        repeat(count) {
            list.add(makeRelatedWordDescribe())
        }
        return list
    }

    fun makeRelatedWordAdjective() = RelatedWordAdjective(DataFactory.randomString(),
            DataFactory.randomInteger())

    fun makeListOfRelatedWordAdjective(count: Int): List<RelatedWordAdjective> {
        val list = mutableListOf<RelatedWordAdjective>()
        repeat(count) {
            list.add(makeRelatedWordAdjective())
        }
        return list
    }

    fun makeRelatedWordTriggered() = RelatedWordTriggered(DataFactory.randomString(),
            DataFactory.randomInteger())

    fun makeListOfRelatedWordTriggered(count: Int): List<RelatedWordTriggered> {
        val list = mutableListOf<RelatedWordTriggered>()
        repeat(count) {
            list.add(makeRelatedWordTriggered())
        }
        return list
    }
}