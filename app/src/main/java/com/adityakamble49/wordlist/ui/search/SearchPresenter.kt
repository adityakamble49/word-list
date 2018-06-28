package com.adityakamble49.wordlist.ui.search

import com.adityakamble49.wordlist.interactor.GetAllWords
import com.adityakamble49.wordlist.interactor.GetCurrentWordList
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Search Presenter
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
class SearchPresenter @Inject constructor(
        private val view: SearchContract.View,
        private val getAllWords: GetAllWords,
        private val getCurrentWordList: GetCurrentWordList) : SearchContract.Presenter {

    private lateinit var viewModel: SearchViewModel

    override fun initialize() {
        getAllWords()
        fetchCurrentWordList()
    }

    override fun onStop() {
        getAllWords.dispose()
        getCurrentWordList.dispose()
    }

    override fun setViewModel(viewModel: SearchViewModel) {
        this.viewModel = viewModel
    }

    override fun onClickAddWordAlert() {
        view.openAddWordUI()
    }

    private fun getAllWords() {
        getAllWords.execute(GetAllWordsSubscriber())
    }

    private inner class GetAllWordsSubscriber : DisposableSingleObserver<List<Word>>() {
        override fun onError(e: Throwable) {}

        override fun onSuccess(t: List<Word>) {
            viewModel.allWordsList = t
            updateAllWordList(viewModel.allWordsList)
        }
    }

    private fun fetchCurrentWordList() {
        getCurrentWordList.execute(GetCurrentWordListSubscriber())
    }

    private inner class GetCurrentWordListSubscriber : DisposableSingleObserver<WordList>() {
        override fun onError(e: Throwable) {}

        override fun onSuccess(t: WordList) {
            view.toggleAddWordAlert(t.marketplaceFilename.isEmpty())
        }
    }

    private fun updateAllWordList(wordList: List<Word>) {
        view.updateAllWordList(wordList)
    }
}