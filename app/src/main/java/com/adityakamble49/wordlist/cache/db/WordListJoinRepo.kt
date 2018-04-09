package com.adityakamble49.wordlist.cache.db

import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.model.WordListJoin
import javax.inject.Inject

/**
 * Word List Join Repo
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@PerApplication
class WordListJoinRepo @Inject constructor(
        private val wordListJoinDao: WordListJoinDao) {

    fun insert(wordListJoin: WordListJoin) = wordListJoinDao.insert(wordListJoin)

    fun insertList(listOfWordListJoin: List<WordListJoin>) =
            wordListJoinDao.insertList(listOfWordListJoin)

    fun getWords(selectedWordListId: Int) = wordListJoinDao.getWordsForWordList(selectedWordListId)
}