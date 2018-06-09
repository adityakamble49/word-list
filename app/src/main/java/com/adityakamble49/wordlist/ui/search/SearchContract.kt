package com.adityakamble49.wordlist.ui.search

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * Search Contract
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
interface SearchContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun updateAllWordList(wordList: List<Word>)

    }

    interface Presenter : BaseContract.Presenter {
        fun setViewModel(viewModel: SearchViewModel)
    }
}