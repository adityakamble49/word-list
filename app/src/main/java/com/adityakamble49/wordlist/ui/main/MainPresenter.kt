package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.interactor.AreWordsImportedUseCase
import com.adityakamble49.wordlist.interactor.ImportWordListToDatabaseUseCase
import com.adityakamble49.wordlist.interactor.UpdateCurrentLoadedListIdUseCase
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.word.WordActivity
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Main Activity Presenter
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
class MainPresenter @Inject constructor(
        private val view: MainContract.View,
        private val areWordsImportedUseCase: AreWordsImportedUseCase,
        private val updateCurrentLoadedListIdUseCase: UpdateCurrentLoadedListIdUseCase,
        private val importWordListToDatabaseUseCase: ImportWordListToDatabaseUseCase) :
        MainContract.Presenter {

    private lateinit var viewModel: MainActivityViewModel

    override fun initialize() {
        checkIfWordsImported()
        observeWordLists()
    }

    override fun setViewModel(viewModel: MainActivityViewModel) {
        this.viewModel = viewModel
    }

    private fun startWordImportProcedure() {
        view.showLoadingDialog(true)
        importWordListToDatabaseUseCase.execute().subscribe(ImportWordListToDatabaseSubscriber())
    }

    private fun checkIfWordsImported() {
        if (!areWordsImportedUseCase.execute()) {
            startWordImportProcedure()
        } else {
            view.dataInitialized()
        }
    }

    private inner class ImportWordListToDatabaseSubscriber : CompletableObserver {

        override fun onComplete() {
            view.showLoadingDialog(false)
            view.dataInitialized()
        }

        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {}
    }

    override fun onClickedLoadList() {
        view.showLoadSavedListDialog()
    }

    override fun onClickedAbout() {
        view.startAboutActivity()
    }

    override fun onClickedSavedListItem(selectedWordList: WordList) {
        updateCurrentLoadedListIdUseCase.execute(selectedWordList.id)
        viewModel.updateCurrentWordList(selectedWordList)
    }

    private fun observeWordLists() {
        viewModel.savedWordList.observe(view, object : Observer<List<WordList>> {
            override fun onChanged(t: List<WordList>?) {
                t?.let {
                    view.updateSavedWordLists(it)
                }
            }
        })
    }

    override fun onClickLearnWords() {
        view.startWordActivity(WordActivity.Companion.WordActivityMode.LEARN)
    }

    override fun onClickPracticeWords() {
        view.startWordActivity(WordActivity.Companion.WordActivityMode.PRACTICE)
    }

    override fun onBackPressed() {
        view.handleFinishActivity()
    }
}