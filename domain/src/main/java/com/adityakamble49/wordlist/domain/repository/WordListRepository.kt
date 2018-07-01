package com.adityakamble49.wordlist.domain.repository

import com.adityakamble49.wordlist.domain.model.WordList

/**
 * Word List Repository
 * This interface will be implemented by data layer
 * Contract to access Word List related data
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
interface WordListRepository {
    fun getWordLists(): List<WordList>
    fun insertWordList(wordList: WordList): Long
}