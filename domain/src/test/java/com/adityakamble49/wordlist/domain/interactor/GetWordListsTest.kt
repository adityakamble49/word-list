package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import com.adityakamble49.wordlist.domain.test.WordListDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetWordListsTest {

    @Mock private lateinit var wordListRepository: WordListRepository
    @Mock private lateinit var postExecutionThread: PostExecutionThread
    private lateinit var getWordLists: GetWordLists

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getWordLists = GetWordLists(wordListRepository, postExecutionThread)
    }

    @Test
    fun getWordListsCompletes() {
        stubGetWordLists(Observable.just(WordListDataFactory.makeListOfWordList(3)))
        val testObserver = getWordLists.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    private fun stubGetWordLists(observable: Observable<List<WordList>>) {
        whenever(wordListRepository.getWordLists()).thenReturn(observable)
    }
}