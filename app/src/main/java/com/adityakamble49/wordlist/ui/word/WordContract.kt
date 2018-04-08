package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * Word View Pager Contract
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
interface WordContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun initializeActivityMode()
        fun updateWord(word: Word)
        fun updateWordInformation(information: String)
        fun updateWordMnemonic(mnemonic: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun loadWords()
        fun loadWord(wordId: Int)
        fun onClickWordInformation()
        fun onClickWordMnemonic()
    }
}