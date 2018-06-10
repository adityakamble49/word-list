package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.interactor.CreateWordListUseCase
import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.interactor.GetWordListsUseCase
import com.adityakamble49.wordlist.interactor.UpdateCurrentLoadedListIdUseCase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.utils.WordUtils
import io.reactivex.SingleObserver
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
        private val getWordListsUseCase: GetWordListsUseCase,
        private val updateCurrentLoadedListIdUseCase: UpdateCurrentLoadedListIdUseCase,
        private val getCurrentWordListUseCase: GetCurrentWordListUseCase,
        private val createWordListUseCase: CreateWordListUseCase) :
        WordListContract.Presenter {

    private lateinit var wordListViewModel: WordListViewModel

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
        observeWordLists()
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
     * Observe saved word list details to check if any new update in last word id saved. As Word Activity
     * Will updated last word accessed by user in particular word list, it will be used to update bookmark in
     * Main list of Words
     */
    private fun observeSavedWordLists() {
        getWordListsUseCase.execute().observe(view, Observer<List<WordList>> {
            it?.let {
                it.forEach { wordList ->
                    if (wordList.id == wordListViewModel.currentWordList.id) {
                        view.updateBookmarkItem(wordList.lastWordId)
                    }
                }
            }
        })
    }

    override fun onClickSearch() {
        view.openSearch()
    }

    override fun onClickLoadList() {
        view.showLoadSavedListDialog()
    }

    override fun onClickCreateList() {
        view.showCreateListDialog()
    }

    override fun onCreateWordListPositive(wordListName: String) {
        createWordListUseCase.execute(wordListName).subscribe(CreateWordListObserver())
    }

    private inner class CreateWordListObserver : SingleObserver<WordList> {

        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(t: WordList) {
            view.showCreateWordListResponse("Word List Created")
            updateCurrentWordList(t)
        }

        override fun onError(e: Throwable) {
            view.showCreateWordListResponse("Word List Name Exist!")
        }

    }

    override fun onClickSavedListItem(selectedWordList: WordList) {
        updateCurrentWordList(selectedWordList)
    }

    private fun updateCurrentWordList(wordList: WordList) {
        updateCurrentLoadedListIdUseCase.execute(wordList.id)
        wordListViewModel.updateCurrentLoadedSavedList(wordList)
    }

    override fun onClickedSingleWord(word: Word) {

    }

    private fun observeWordLists() {
        wordListViewModel.savedWordList.observe(view,
                Observer<List<WordList>> { t ->
                    t?.let {
                        view.updateSavedWordLists(it)
                    }
                })
    }
}