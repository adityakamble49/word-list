package com.adityakamble49.wordlist.mobileui

import com.adityakamble49.wordlist.presentation.model.WordListView
import java.util.*

/**
 * Word List Data Factory
 * It provides sample data to test Word List
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */
object WordListDataFactory {

    fun randomUUID() = UUID.randomUUID().toString()

    fun randomNumber() = Random().nextInt()

    fun makeWordList() = WordListView(randomNumber(), randomUUID(), randomUUID(), randomUUID(),
            randomNumber())

    fun makeListOfWordList(count: Int): List<WordListView> {
        val wordLists = mutableListOf<WordListView>()
        repeat(count) {
            wordLists.add(makeWordList())
        }
        return wordLists
    }
}