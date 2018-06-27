package com.adityakamble49.wordlist.ui.search

import com.adityakamble49.wordlist.interactor.GetAllWords
import com.adityakamble49.wordlist.interactor.GetCurrentWordList
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Search Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */
class SearchTest {

    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchContract.View
    private lateinit var getAllWords: GetAllWords
    private lateinit var getCurrentWordList: GetCurrentWordList

    @Before
    fun setUp() {
        view = mock(SearchContract.View::class.java)
        getAllWords = mock(GetAllWords::class.java)
        getCurrentWordList = mock(GetCurrentWordList::class.java)
        presenter = SearchPresenter(view, getAllWords, getCurrentWordList)
    }

    @Test
    fun onClickAddWordAlert_openAddWordUI() {
        presenter.onClickAddWordAlert()
        verify(view).openAddWordUI()
    }
}