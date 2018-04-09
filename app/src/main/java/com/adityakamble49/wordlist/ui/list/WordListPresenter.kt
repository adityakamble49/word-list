package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
import com.adityakamble49.wordlist.utils.WordUtils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Word List Presenter
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListPresenter @Inject constructor(
        private val view: WordListContract.View,
        private val preferenceHelper: PreferenceHelper,
        private val wordListRepo: WordListRepo,
        private val getCurrentWordListUseCase: GetCurrentWordListUseCase) :
        WordListContract.Presenter {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var wordListViewModel: WordListViewModel

    fun setMainViewModel(mainActivityViewModel: MainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel
    }

    fun setWordListViewModel(wordListViewModel: WordListViewModel) {
        this.wordListViewModel = wordListViewModel
    }

    override fun initialize() {
        getCurrentWordList()
    }

    private fun observeWordList() {
        wordListViewModel.getWordList().observe(view, Observer<List<Word>> { it ->
            it?.let {
                view.showLoading(false)
                view.updateWordList(WordUtils.sortWordList(it,
                        wordListViewModel.currentWordList.wordSequenceList))
            }
        })
    }

    private fun getCurrentWordList() {
        getCurrentWordListUseCase.execute().subscribe(GetCurrentWordListSubscriber())
    }

    private inner class GetCurrentWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            wordListViewModel.updateCurrentLoadedSavedList(t)
            wordListViewModel.initialize()
            observeWordList()
            observeCurrentWordList()
        }
    }

    private fun observeCurrentWordList() {
        mainActivityViewModel.getCurrentWordList().observe(view, Observer<WordList> { t ->
            t?.let {
                wordListViewModel.updateCurrentLoadedSavedList(it)
            }
        })
    }

    override fun onClickedSingleWord(word: Word) {

    }
}