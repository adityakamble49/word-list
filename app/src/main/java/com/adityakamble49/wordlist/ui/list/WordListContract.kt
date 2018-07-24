package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
interface WordListContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun showLoading(toShow: Boolean)
        fun updateSavedWordLists(savedWordLists: List<WordList>)
        fun updateCurrentWordListName(wordListName: String)
        fun showCreateListDialog()
        fun showMessage(response: String)
        fun openAddWordUI()
        fun showLoadSavedListDialog()
        fun updateWords(wordList: List<Word>)
        fun openSearch()
        fun openSingleWord(word: Word)
        fun updateBookmarkItem(bookmarkItemId: Int)
        fun updateMenus(wordList: WordList)
    }

    interface Presenter : BaseContract.Presenter {
        fun setWordListViewModel(wordListViewModel: WordListViewModel)
        fun onClickSearch()
        fun onClickLoadList()
        fun onClickImportList()
        fun onClickExportList()
        fun requestUpdateWordList()
        fun onClickCreateList()
        fun onClickAddWord()
        fun onCreateWordListPositive(wordListName: String)
        fun onClickSavedListItem(selectedWordList: WordList)
        fun onClickedSingleWord(word: Word)
    }
}