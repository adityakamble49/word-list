package com.adityakamble49.wordlist.ui.main

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.interactor.ImportWordListToDatabaseUseCase
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
class MainPresenter @Inject constructor(
        private val view: MainContract.View,
        private val preferenceHelper: PreferenceHelper,
        private val importWordListToDatabaseUseCase: ImportWordListToDatabaseUseCase) :
        MainContract.Presenter {

    private lateinit var viewModel: MainActivityViewModel

    override fun initialize() {
        checkIfWordsImported()
    }

    fun setViewModel(viewModel: MainActivityViewModel) {
        this.viewModel = viewModel
    }

    private fun checkIfWordsImported() {
        if (!preferenceHelper.areWordsImported) {
            startWordImportProcedure()
        }
    }

    private fun startWordImportProcedure() {
        view.showLoadingDialog(true)
        importWordListToDatabaseUseCase.execute().subscribe(ImportWordListToDatabaseSubscriber())
    }

    override fun onClickedChangeListType() {
        view.showChangeListTypeDialog(preferenceHelper.currentListType)
    }

    override fun onListTypeSelected(wordListType: Int) {
        preferenceHelper.currentListType = wordListType
        view.alertListTypeUpdate(wordListType)
    }

    private inner class ImportWordListToDatabaseSubscriber : CompletableObserver {

        override fun onComplete() {
            view.showLoadingDialog(false)
        }

        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {}
    }
}