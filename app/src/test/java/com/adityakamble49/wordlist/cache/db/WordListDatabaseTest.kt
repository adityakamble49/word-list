package com.adityakamble49.wordlist.cache.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.adityakamble49.wordlist.cache.entities.WordListWordJoin
import com.adityakamble49.wordlist.test.DataFactory
import com.adityakamble49.wordlist.test.WordDataFactory
import com.adityakamble49.wordlist.test.WordListDataFactory
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Word List Database Test
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@RunWith(RobolectricTestRunner::class)
class WordListDatabaseTest {

    @Rule
    @JvmField
    var instantTaskExecutableRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, WordListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private val wordDao = database.wordDao()
    private val wordListDao = database.wordListDao()
    private val wordListWordJoinDao = database.wordListWordJoinDao()

    @Test
    fun insertWordCompletes() {
        val words = WordDataFactory.makeWords(100)
        val testResult = wordDao.insertWords(words)
        assertThat(testResult, not(hasItem(-1L)))
    }

    @Test
    fun updateWordCompletes() {
        val words = WordDataFactory.makeWords(100)
        val wordToUpdate = words[20]
        wordToUpdate.information = DataFactory.randomString()
        wordToUpdate.mnemonics = DataFactory.randomString()
        wordDao.insertWords(words)
        val testResult = wordDao.updateWord(wordToUpdate)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun deleteWordCompletes() {
        val words = WordDataFactory.makeWords(100)
        val wordToDelete = words[20]
        wordDao.insertWords(words)
        val testResult = wordDao.deleteWord(wordToDelete)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun deleteNonExistingWordCompletes() {
        val words = WordDataFactory.makeWords(100)
        val wordToDelete = WordDataFactory.makeWord(125)
        wordDao.insertWords(words)
        val testResult = wordDao.deleteWord(wordToDelete)
        assertThat(testResult, `is`(0))
    }

    @Test
    fun getWordReturnsData() {
        val words = WordDataFactory.makeWords(100)
        wordDao.insertWords(words)
        val testObserver = wordDao.getWords().test()
        testObserver.assertValue(words)
    }

    @Test
    fun getWordByIdReturnsData() {
        val words = WordDataFactory.makeWords(100)
        val wordToFetch = words[45]
        wordDao.insertWords(words)
        val testObserver = wordDao.getWordById(wordToFetch.id).test()
        testObserver.assertValue(wordToFetch)
    }

    @Test
    fun insertWordListCompletes() {
        val wordList = WordListDataFactory.makeWordList(1)
        val testResult = wordListDao.insert(wordList)
        assertThat(testResult, not(-1L))
    }

    @Test
    fun insertListOfWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        val testResult = wordListDao.insertList(listOfWordList)
        assertThat(testResult, not(hasItem(-1L)))
    }

    @Test
    fun updateWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        wordListDao.insertList(listOfWordList)
        val wordListToUpdate = listOfWordList[5]
        wordListToUpdate.name = DataFactory.randomString()
        val testResult = wordListDao.update(wordListToUpdate)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun deleteWordListCompletes() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        wordListDao.insertList(listOfWordList)
        val wordListToDelete = listOfWordList[5]
        wordListToDelete.name = DataFactory.randomString()
        val testResult = wordListDao.delete(wordListToDelete)
        assertThat(testResult, `is`(1))
    }

    @Test
    fun getWordListReturnsData() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        wordListDao.insertList(listOfWordList)
        val testObserver = wordListDao.getWordList().test()
        testObserver.assertValue(listOfWordList)
    }

    @Test
    fun getWordListByIdReturnsData() {
        val listOfWordList = WordListDataFactory.makeListOfWordList(10)
        val wordListToFetch = listOfWordList[5]
        wordListDao.insertList(listOfWordList)
        val testObserver = wordListDao.getWordListById(wordListToFetch.id).test()
        testObserver.assertValue(wordListToFetch)
    }

    @Test
    fun insertAndGetWordInWordList() {
        val word = WordDataFactory.makeWord(1)
        val wordList = WordListDataFactory.makeWordList(1)
        val insertedWordId = wordDao.insertWord(word)
        val insertedWordListId = wordListDao.insert(wordList)
        val wordListWordJoin = WordListWordJoin(insertedWordListId.toInt(), insertedWordId.toInt())
        wordListWordJoinDao.insert(wordListWordJoin)
        val testObserver = wordListWordJoinDao.getWordsInWordList(insertedWordListId.toInt()).test()
        testObserver.assertValue(listOf(word))
    }

    @Test
    fun insertAndGetWordsInWordList() {
        val words = WordDataFactory.makeWords(10)
        val wordList = WordListDataFactory.makeWordList(1)
        val insertedWordIds = wordDao.insertWords(words)
        val insertedWordListId = wordListDao.insert(wordList)
        val listOfWordListWordJoin = mutableListOf<WordListWordJoin>()
        words.forEachIndexed { index, word ->
            listOfWordListWordJoin.add(WordListWordJoin(insertedWordListId.toInt(),
                    insertedWordIds[index].toInt()))
        }
        wordListWordJoinDao.insertList(listOfWordListWordJoin)

        val testObserver = wordListWordJoinDao.getWordsInWordList(insertedWordListId.toInt()).test()
        testObserver.assertValue(words)
    }

    @Test
    fun deleteWordFromWordList() {
        val words = WordDataFactory.makeWords(10)
        val wordList = WordListDataFactory.makeWordList(1)
        val insertedWordIds = wordDao.insertWords(words)
        val insertedWordListId = wordListDao.insert(wordList)
        val listOfWordListWordJoin = mutableListOf<WordListWordJoin>()
        words.forEachIndexed { index, word ->
            listOfWordListWordJoin.add(WordListWordJoin(insertedWordListId.toInt(),
                    insertedWordIds[index].toInt()))
        }
        wordListWordJoinDao.insertList(listOfWordListWordJoin)
        val wordToDelete = words[5]
        val deletedResult = wordListWordJoinDao.delete(
                WordListWordJoin(insertedWordListId.toInt(), wordToDelete.id))
        assertThat(deletedResult, `is`(1))

        words.remove(wordToDelete)
        val testObserver = wordListWordJoinDao.getWordsInWordList(insertedWordListId.toInt()).test()
        testObserver.assertValue(words)
    }
}