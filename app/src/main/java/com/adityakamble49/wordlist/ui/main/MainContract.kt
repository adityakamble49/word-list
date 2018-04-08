package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
interface MainContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun showLoadingDialog(toShow: Boolean, title: String = "Working",
                              content: String = "Working on something")

        fun showChangeListTypeDialog(selectedWordListType: Int)
        fun alertListTypeUpdate(wordListType: Int)
    }

    interface Presenter : BaseContract.Presenter {
        fun onClickedChangeListType()
        fun onListTypeSelected(wordListType: Int)
    }
}