package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * Word List Presenter
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListPresenter @Inject constructor(
        private val view: WordListContract.View,
        private val preferenceHelper: PreferenceHelper) :
        WordListContract.Presenter {

    private lateinit var viewModel: WordListViewModel

    fun setViewModel(viewModel: WordListViewModel) {
        this.viewModel = viewModel
    }

    fun initialize() {
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