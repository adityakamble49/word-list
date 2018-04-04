package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.model.Word

/**
 * Word List Presenter
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListPresenter constructor(private val view: WordListContract.View,
                                    private val viewModel: WordListViewModel) :
        WordListContract.Presenter {

    init {
        observeWordList()
    }

    private fun observeWordList() {
        viewModel.getWordList().observe(view, Observer<List<Word>> { it ->
            it?.let {
                view.showLoading(false)
                view.updateWordList(it)
            }
        })
    }

    override fun onClickedSingleWord(word: Word) {

    }
}