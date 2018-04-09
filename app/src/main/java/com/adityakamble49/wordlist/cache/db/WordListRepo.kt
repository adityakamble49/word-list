package com.adityakamble49.wordlist.cache.db

import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.model.WordList
import javax.inject.Inject

/**
 * Word List Repo
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@PerApplication
class WordListRepo @Inject constructor(private val wordListDao: WordListDao) {

    fun insert(wordList: WordList) = wordListDao.insert(wordList)

    fun insertList(listOfWordList: List<WordList>) = wordListDao.insertList(listOfWordList)

    fun getWordLists() = wordListDao.getWordLists()

    fun getWordListById(currentWordListId: Int) =
            wordListDao.getWordListByIdDirect(currentWordListId)
}