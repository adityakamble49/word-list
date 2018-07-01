package com.adityakamble49.wordlist.domain.test

import com.adityakamble49.wordlist.domain.model.WordList
import java.util.*

/**
 * Word List Data Factory
 * It provides sample data to test Word List
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
object WordListDataFactory {

    fun randomUUID() = UUID.randomUUID().toString()

    fun randomNumber() = Random().nextInt()

    fun makeWordList() = WordList(randomNumber(), randomUUID(), randomUUID(), randomUUID(),
            randomNumber())

    fun makeListOfWordList(count: Int): List<WordList> {
        val wordLists = mutableListOf<WordList>()
        repeat(count) {
            wordLists.add(makeWordList())
        }
        return wordLists
    }
}