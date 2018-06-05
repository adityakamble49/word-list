package com.adityakamble49.wordlist.ui.main

import com.adityakamble49.wordlist.interactor.AreWordsImportedUseCase
import com.adityakamble49.wordlist.interactor.ImportWordListToDatabaseUseCase
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
        private val importWordListToDatabaseUseCase: ImportWordListToDatabaseUseCase) :
        MainContract.Presenter {

    private lateinit var viewModel: MainActivityViewModel

    override fun initialize() {
        checkIfWordsImported()
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

    override fun onClickWordList() {
        view.openWordList()
    }

    override fun onClickSettings() {
        view.openSettings()
    }

    override fun onClickAbout() {
        view.openAbout()
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