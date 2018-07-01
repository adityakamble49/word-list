package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
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
        stubCreateWordList()
        val testObserver = createWordList.buildSingleUseCase(
                CreateWordList.Params.forWordList(WordListDataFactory.randomUUID())).test()
        testObserver.assertComplete()
    }

    private fun stubCreateWordList() {
        whenever(wordListRepository.getWordLists()).thenReturn(
                WordListDataFactory.makeListOfWordList(2))
        whenever(wordListRepository.insertWordList(any())).thenReturn(1)
    }
}