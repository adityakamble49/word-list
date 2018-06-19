package com.adityakamble49.wordlist.ui.list

import com.adityakamble49.wordlist.interactor.CreateWordList
import com.adityakamble49.wordlist.interactor.GetCurrentWordList
import com.adityakamble49.wordlist.interactor.GetWordLists
import com.adityakamble49.wordlist.interactor.UpdateCurrentLoadedListId
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Word List Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */
class WordListTest {

    @Mock private lateinit var getWordLists: GetWordLists
    @Mock private lateinit var updateCurrentLoadedListId: UpdateCurrentLoadedListId
    @Mock private lateinit var getCurrentWordList: GetCurrentWordList
    @Mock private lateinit var createWordList: CreateWordList
    @InjectMocks private lateinit var presenter: WordListPresenter
    @Mock private lateinit var view: WordListContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onClickSearch_openSearch() {
        presenter.onClickSearch()
        verify(view).openSearch()
    }

    @Test
    fun onClickLoadList_showLoadSavedListDialog() {
        presenter.onClickLoadList()
        verify(view).showLoadSavedListDialog()
    }
}