package com.adityakamble49.wordlist.ui.list

import com.adityakamble49.wordlist.interactor.CreateWordListUseCase
import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.interactor.GetWordListsUseCase
import com.adityakamble49.wordlist.interactor.UpdateCurrentLoadedListIdUseCase
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

    @Mock private lateinit var getWordListsUseCase: GetWordListsUseCase
    @Mock private lateinit var updateCurrentLoadedListIdUseCase: UpdateCurrentLoadedListIdUseCase
    @Mock private lateinit var getCurrentWordListUseCase: GetCurrentWordListUseCase
    @Mock private lateinit var createWordListUseCase: CreateWordListUseCase
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