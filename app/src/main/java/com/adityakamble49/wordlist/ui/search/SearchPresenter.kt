package com.adityakamble49.wordlist.ui.search

import com.adityakamble49.wordlist.interactor.GetAllWordsUseCase
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Search Presenter
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
class SearchPresenter @Inject constructor(
        private val view: SearchContract.View,
        private val getAllWordsUseCase: GetAllWordsUseCase) : SearchContract.Presenter {

    private lateinit var viewModel: SearchViewModel

    override fun initialize() {
        getAllWords()
    }

    override fun setViewModel(viewModel: SearchViewModel) {
        this.viewModel = viewModel
    }

    private fun getAllWords() {
        getAllWordsUseCase.execute().subscribe(GetAllWordsSubscriber())
    }

    private inner class GetAllWordsSubscriber : Observer<List<Word>> {
        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            viewModel.allWordsList = t
            updateAllWordList(viewModel.allWordsList)
        }
    }

    private fun updateAllWordList(wordList: List<Word>) {
        view.updateAllWordList(wordList)
    }
}