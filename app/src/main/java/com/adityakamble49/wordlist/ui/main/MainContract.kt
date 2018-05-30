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
        fun updateSavedWordLists(savedWordLists: List<WordList>)
        fun showLoadSavedListDialog()
        fun alertListTypeUpdate(wordListType: Int)
        fun startWordActivity(wordActivityMode: WordActivity.Companion.WordActivityMode)
        fun startSettingsActivity()
        fun startAboutActivity()
        fun handleFinishActivity()
    }

    interface Presenter : BaseContract.Presenter {
        fun setViewModel(viewModel: MainActivityViewModel)
        fun onClickedLoadList()
        fun onClickedSettings()
        fun onClickedAbout()
        fun onClickedSavedListItem(selectedWordList: WordList)
        fun onClickLearnWords()
        fun onClickPracticeWords()
        fun onBackPressed()
    }
}