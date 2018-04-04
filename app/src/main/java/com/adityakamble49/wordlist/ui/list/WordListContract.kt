package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.Word

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
interface WordListContract {

    interface View : LifecycleOwner {
        fun showLoading(toShow: Boolean)
        fun updateWordList(wordList: List<Word>)
        fun openSingleWord(word: Word)
    }

    interface Presenter {
        fun onClickedSingleWord(word: Word)
    }
}