package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.LifecycleOwner

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
interface MainContract {

    interface View : LifecycleOwner {
        fun showChangeListTypeDialog(selectedWordListType: Int)
        fun alertListTypeUpdate(wordListType: Int)
    }

    interface Presenter {
        fun onClickedChangeListType()
        fun onListTypeSelected(wordListType: Int)
    }
}