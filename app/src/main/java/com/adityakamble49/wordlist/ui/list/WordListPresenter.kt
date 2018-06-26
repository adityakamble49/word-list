package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.interactor.CreateWordList
import com.adityakamble49.wordlist.interactor.GetCurrentWordList
import com.adityakamble49.wordlist.interactor.GetWordLists
import com.adityakamble49.wordlist.interactor.UpdateCurrentLoadedListId
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
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
        private val getWordLists: GetWordLists,
        private val updateCurrentLoadedListId: UpdateCurrentLoadedListId,
        private val getCurrentWordList: GetCurrentWordList,
        private val createWordList: CreateWordList) :
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
        getCurrentWordList.execute().subscribe(GetCurrentWordListSubscriber())
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
            view.updateMenus(t)
            wordListViewModel.initialize()
            observeWords()
            observeSavedWordLists()
            view.updateCurrentWordListName(t.name)
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
                view.updateWords(it)
            }
        })
    }

    /**
     * Observe saved word list details to check if any new update in last word id saved. As Word Activity
     * Will updated last word accessed by user in particular word list, it will be used to update bookmark in
     * Main list of Words
     */
    private fun observeSavedWordLists() {
        getWordLists.execute().observe(view, Observer<List<WordList>> {
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

    override fun requestUpdateWordList() {
        getCurrentWordList.execute().subscribe(RequestUpdateWordListSubscriber())
    }

    private inner class RequestUpdateWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            wordListViewModel.updateCurrentLoadedSavedList(t)
            view.updateMenus(t)
        }
    }

    override fun onClickCreateList() {
        view.showCreateListDialog()
    }

    override fun onClickAddWord() {
        view.openAddWordUI()
    }

    override fun onCreateWordListPositive(wordListName: String) {
        if (!wordListName.isEmpty()) {
            createWordList.execute(wordListName).subscribe(CreateWordListObserver())
        } else {
            view.showMessage("Word List Name Empty!")
        }
    }

    private inner class CreateWordListObserver : SingleObserver<WordList> {

        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(t: WordList) {
            view.showMessage("Word List Created")
            updateCurrentWordList(t)
        }

        override fun onError(e: Throwable) {
            view.showMessage("Word List Name Exist!")
        }

    }

    override fun onClickSavedListItem(selectedWordList: WordList) {
        updateCurrentWordList(selectedWordList)
        view.updateCurrentWordListName(selectedWordList.name)
    }

    private fun updateCurrentWordList(wordList: WordList) {
        updateCurrentLoadedListId.execute(wordList.id)
        wordListViewModel.updateCurrentLoadedSavedList(wordList)
        view.updateMenus(wordList)
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