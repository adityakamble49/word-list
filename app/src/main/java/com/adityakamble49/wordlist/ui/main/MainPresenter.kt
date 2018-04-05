package com.adityakamble49.wordlist.ui.main

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
class MainPresenter constructor(private val view: MainContract.View,
                                private val viewModel: MainActivityViewModel) :
        MainContract.Presenter {

    override fun onClickedChangeListType() {
        view.showChangeListTypeDialog(viewModel.getCurrentListType())
    }

    override fun onListTypeSelected(wordListType: Int) {
        viewModel.changeCurrentListType(wordListType)
        view.updateListType(wordListType)
    }
}