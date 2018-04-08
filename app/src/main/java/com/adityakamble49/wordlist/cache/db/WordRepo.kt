package com.adityakamble49.wordlist.cache.db

import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@PerApplication
class WordRepo @Inject constructor(private var wordDao: WordDao) {

    fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    fun insertWords(wordList: List<Word>) {
        wordDao.insertWords(wordList)
    }

    fun getWordList() = wordDao.getWordList()

    fun getWordList(listType: Int) = wordDao.getWordList(listType)

    fun getWordById(id: Int) = wordDao.getWordById(id)
}