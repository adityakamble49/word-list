package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseContract
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
interface WordListContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun showLoading(toShow: Boolean)
        fun updateSavedWordLists(savedWordLists: List<WordList>)
        fun showLoadSavedListDialog()
        fun updateWords(wordList: List<Word>)
        fun openSingleWord(word: Word)
        fun updateBookmarkItem(bookmarkItemId: Int)
    }

    interface Presenter : BaseContract.Presenter {
        fun setWordListViewModel(wordListViewModel: WordListViewModel)
        fun onClickLoadList()
        fun onClickSavedListItem(selectedWordList: WordList)
        fun onClickedSingleWord(word: Word)
    }
}