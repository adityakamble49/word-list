package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.exceptions.WordListNameExistException
import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import com.adityakamble49.wordlist.domain.test.WordListDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Create Word List Tests
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
class CreateWordListTest {

    private lateinit var createWordList: CreateWordList
    @Mock private lateinit var wordListRepository: WordListRepository
    @Mock private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        createWordList = CreateWordList(wordListRepository, postExecutionThread)
    }

    @Test
    fun createWordListCompletes() {
        stubCreateWordList(WordListDataFactory.makeListOfWordList(3))
        val testObserver = createWordList.buildSingleUseCase(
                CreateWordList.Params.forWordList(WordListDataFactory.randomUUID())).test()
        testObserver.assertComplete()
    }

    @Test
    fun createWordListReturnWordList() {
        stubCreateWordList(WordListDataFactory.makeListOfWordList(3))
        val wordListName = WordListDataFactory.randomUUID()
        val testObserver = createWordList.buildSingleUseCase(
                CreateWordList.Params.forWordList(wordListName)).test()
        testObserver.assertValue { v -> v.name == wordListName }
    }

    @Test(expected = WordListNameExistException::class)
    fun createWordListDuplicateName() {
        val sampleWordList = WordListDataFactory.makeListOfWordList(3)
        stubCreateWordList(sampleWordList)
        createWordList.buildSingleUseCase(
                CreateWordList.Params.forWordList(sampleWordList[0].name)).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun createWordListThrowsIllegalException() {
        stubCreateWordList(WordListDataFactory.makeListOfWordList(3))
        createWordList.buildSingleUseCase().test()
    }

    private fun stubCreateWordList(listOfWordList: List<WordList>) {
        whenever(wordListRepository.getWordLists()).thenReturn(listOfWordList)
        whenever(wordListRepository.insertWordList(any())).thenReturn(1)
    }
}