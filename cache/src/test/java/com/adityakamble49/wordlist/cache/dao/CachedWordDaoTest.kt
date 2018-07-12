package com.adityakamble49.wordlist.cache.dao

import com.adityakamble49.wordlist.cache.test.DataFactory.randomString
import com.adityakamble49.wordlist.cache.test.WordDataFactory.makeCachedWord
import com.adityakamble49.wordlist.cache.test.WordDataFactory.makeCachedWords
import com.adityakamble49.wordlist.cache.test.sortCachedWord
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * CachedWord Dao Test
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class CachedWordDaoTest : DaoTest() {

    private val dao: CachedWordDao = database.cachedWordDao()

    @Test
    fun insertWordReturnsId() {
        val word = makeCachedWord()
        val wordId = dao.insertWord(word)
        assertNotNull(wordId)
    }

    @Test
    fun insertWordsReturnIds() {
        val words = makeCachedWords(10)
        val wordIds = dao.insertWords(words)
        assertNotNull(wordIds)
    }

    @Test
    fun updateWordCompletes() {
        // Insert Word
        val word = makeCachedWord()
        val wordId = dao.insertWord(word)

        // Update word
        word.information = randomString()
        word.mnemonic = randomString()
        dao.updateWord(word)

        // Check word if is updated
        val testObserver = dao.getWordById(wordId.toInt()).test()
        testObserver.assertValue(word)
    }

    @Test
    fun deleteWordCompletes() {
        // Insert Word
        val word = makeCachedWord()
        val wordId = dao.insertWord(word)

        // Delete Word
        dao.deleteWord(word)

        // Get word by id and asset empty
        val testObserver = dao.getWordById(wordId.toInt()).test()
        testObserver.assertEmpty()
    }

    @Test
    fun deleteWordByListIdCompletes(){
        // Insert Words
        val words = makeCachedWords(10)
        dao.insertWords(words)

        // Delete Words By List Id
        val selectedListId = words[0].listId
        dao.deleteWordsByListId(selectedListId)

        // Get Words by listId and assert empty
        val testObserver = dao.getWordsByListId(selectedListId).test()
        testObserver.assertValues(listOf())
    }

    @Test
    fun getWordsCompletes() {
        // Insert Words
        val words = makeCachedWords(10)
        words.sortCachedWord()
        dao.insertWords(words)

        // Get Words
        val testObserver = dao.getWords().test()
        testObserver.assertValues(words)
    }

    @Test
    fun getWordByIdReturnsData() {
        // Insert Word
        val word = makeCachedWord()
        val wordId = dao.insertWord(word)
        word.id = wordId.toInt()

        // Get Word By Id
        val testObserver = dao.getWordById(wordId.toInt()).test()
        testObserver.assertValue(word)
    }

    @Test
    fun getWordsByIdReturnsData() {
        // Insert Words
        val words = makeCachedWords(10)
        words.sortCachedWord()
        dao.insertWords(words)

        val selectedListId = words[0].listId

        // Get Words
        val testObserver = dao.getWordsByListId(selectedListId).test()
        testObserver.assertValue {
            it.forEach {
                if (it.listId != selectedListId) {
                    return@assertValue false
                }
            }
            return@assertValue true
        }
    }
}