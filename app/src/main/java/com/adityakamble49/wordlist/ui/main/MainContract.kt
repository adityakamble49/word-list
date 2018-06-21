package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseContract
import com.adityakamble49.wordlist.ui.word.WordActivity

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
interface MainContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun showLoadingDialog(toShow: Boolean, title: String = "Working",
                              content: String = "Working on something")

        fun dataInitialized()
        fun alertListTypeUpdate(wordListType: Int)
        fun startWordActivity(wordActivityMode: WordActivity.Companion.WordActivityMode)
        fun openWordList()
        fun openSettings()
        fun openAbout()
        fun resetNavigationHistory()
        fun handleFinishActivity()
    }

    interface Presenter : BaseContract.Presenter {
        fun setViewModel(viewModel: MainActivityViewModel)
        fun onClickWordList()
        fun onClickSettings()
        fun onClickAbout()
        fun onClickLearnWords()
        fun onClickPracticeWords()
        fun onResume()
        fun onBackPressed()
    }
}