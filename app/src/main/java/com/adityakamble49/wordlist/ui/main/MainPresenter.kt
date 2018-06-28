package com.adityakamble49.wordlist.ui.main

import com.adityakamble49.wordlist.interactor.AreWordsImported
import com.adityakamble49.wordlist.interactor.ImportWordListToDatabase
import com.adityakamble49.wordlist.ui.word.WordActivity
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

/**
 * Main Activity Presenter
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
class MainPresenter @Inject constructor(
        private val view: MainContract.View,
        private val areWordsImported: AreWordsImported,
        private val importWordListToDatabase: ImportWordListToDatabase) :
        MainContract.Presenter {

    private lateinit var viewModel: MainActivityViewModel

    override fun initialize() {
        checkIfWordsImported()
    }

    override fun onStop() {
        importWordListToDatabase.dispose()
    }

    override fun setViewModel(viewModel: MainActivityViewModel) {
        this.viewModel = viewModel
    }

    private fun startWordImportProcedure() {
        view.showLoadingDialog(true)
        importWordListToDatabase.execute(ImportWordListToDatabaseSubscriber())
    }

    private fun checkIfWordsImported() {
        if (!areWordsImported.execute()) {
            startWordImportProcedure()
        } else {
            view.dataInitialized()
        }
    }

    private inner class ImportWordListToDatabaseSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            view.showLoadingDialog(false)
            view.dataInitialized()
        }

        override fun onError(e: Throwable) {}
    }

    override fun onClickWordList() {
        view.openWordList()
    }

    override fun onClickMarketplace() {
        view.openMarketplace()
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

    override fun onResume() {
        view.resetNavigationHistory()
    }

    override fun onBackPressed() {
        view.handleFinishActivity()
    }
}