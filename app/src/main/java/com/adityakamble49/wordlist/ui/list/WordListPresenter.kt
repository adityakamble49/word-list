package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
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

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var wordListViewModel: WordListViewModel

    fun setMainViewModel(mainActivityViewModel: MainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel
    }

    fun setWordListViewModel(wordListViewModel: WordListViewModel) {
        this.wordListViewModel = wordListViewModel
    }

    fun initialize() {
        observeWordList()
        observeCurrentWordListType()
    }

    private fun observeWordList() {
        wordListViewModel.getWordList().observe(view, Observer<List<Word>> { it ->
            it?.let {
                view.showLoading(false)
                view.updateWordList(it)
            }
        })
    }

    private fun observeCurrentWordListType() {
        mainActivityViewModel.currentListType.observe(view, Observer<Int> { t ->
            t?.let {
                wordListViewModel.updateCurrentWordListType(t)
            }
        })
    }

    override fun onClickedSingleWord(word: Word) {

    }
}