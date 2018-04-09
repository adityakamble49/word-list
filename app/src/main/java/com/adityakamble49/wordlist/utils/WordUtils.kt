package com.adityakamble49.wordlist.utils

import com.adityakamble49.wordlist.model.Word

/**
 * Word Utils
 *
 * @author Aditya Kamble
 * @since 10/4/2018
 */
object WordUtils {

    fun sortWordList(wordList: List<Word>, wordSequenceList: List<Int>): List<Word> {
        val sortedList = mutableListOf<Word>()
        wordSequenceList.forEach { ws ->
            wordList.forEach { word ->
                if (word.id == ws) {
                    sortedList.add(word)
                }
            }
        }
        return sortedList
    }
}