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

    /**
     * Set Main Activity ViewModel to access Common data
     */
    override fun setMainViewModel(mainActivityViewModel: MainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel
    }

    /**
     * Set Word List ViewModel to access word list related data
     */
    override fun setWordListViewModel(wordListViewModel: WordListViewModel) {
        this.wordListViewModel = wordListViewModel
    }

    /**
     * Initialize Presenter
     */
    override fun initialize() {
        getCurrentWordList()
    }

    /**
     * Get Current selected word list whose id is saved in preferences and that word list
     * details can be fetched from db
     */
    private fun getCurrentWordList() {
        getCurrentWordListUseCase.execute().subscribe(GetCurrentWordListSubscriber())
    }

    /**
     * Listen to Current WordList response and initiate other observers to do work
     */
    private inner class GetCurrentWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            wordListViewModel.updateCurrentLoadedSavedList(t)
            wordListViewModel.initialize()
            observeWords()
            observeCurrentWordList()
            observeSavedWordLists()
        }
    }

    /**
     * Observe words for displaying in List which is dependent on which word list is selected as
     * Current Word list
     */
    private fun observeWords() {
        wordListViewModel.getWordList().observe(view, Observer<List<Word>> { it ->
            it?.let {
                view.showLoading(false)
                view.updateBookmarkItem(wordListViewModel.currentWordList.lastWordId)
                view.updateWords(WordUtils.sortWords(it,
                        wordListViewModel.currentWordList.wordSequenceList))
            }
        })
    }

    /**
     * Observe current word list which is kept in Main View Model it's and can be changed from
     * Main Activity by choosing specific word list from list of available word list
     */
    private fun observeCurrentWordList() {
        mainActivityViewModel.getCurrentWordList().observe(view, Observer<WordList> { t ->
            t?.let {
                wordListViewModel.updateCurrentLoadedSavedList(it)
            }
        })
    }

    /**
     * Observe saved word list details to check if any new update in last word id saved. As Word Activity
     * Will updated last word accessed by user in particular word list, it will be used to update bookmark in
     * Main list of Words
     */
    private fun observeSavedWordLists() {
        wordListRepo.getWordLists().observe(view, Observer<List<WordList>> {
            it?.let {
                it.forEach { wordList ->
                    if (wordList.id == wordListViewModel.currentWordList.id) {
                        view.updateBookmarkItem(wordList.lastWordId)
                    }
                }
            }
        })
    }

    override fun onClickedSingleWord(word: Word) {

    }
}