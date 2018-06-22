package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.IdRes
import com.adityakamble49.wordlist.R
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
        fun openMarketplace()
        fun openSettings()
        fun openAbout()
        fun resetNavigationHistory(@IdRes navigationItem: Int = R.id.nav_wordlist)
        fun handleFinishActivity()
    }

    interface Presenter : BaseContract.Presenter {
        fun setViewModel(viewModel: MainActivityViewModel)
        fun onClickWordList()
        fun onClickMarketplace()
        fun onClickSettings()
        fun onClickAbout()
        fun onClickLearnWords()
        fun onClickPracticeWords()
        fun onResume()
        fun onBackPressed()
    }
}