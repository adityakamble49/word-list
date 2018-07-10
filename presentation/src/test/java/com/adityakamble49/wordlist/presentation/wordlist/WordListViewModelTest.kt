package com.adityakamble49.wordlist.presentation.wordlist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.adityakamble49.wordlist.domain.interactor.CreateWordList
import com.adityakamble49.wordlist.domain.interactor.GetWordLists
import com.adityakamble49.wordlist.presentation.mapper.WordListMapper
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class WordListViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var createWordList: CreateWordList
    @Mock private lateinit var getWordLists: GetWordLists
    @Mock private lateinit var mapper: WordListMapper
    private lateinit var wordlistViewModel: WordListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testDemo() {
        assertTrue(true)
    }
}