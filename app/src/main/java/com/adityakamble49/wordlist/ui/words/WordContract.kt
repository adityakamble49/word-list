package com.adityakamble49.wordlist.ui.words

import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * Word Fragment Contract
 *
 * @author Aditya Kamble
 * @since 7/4/2018
 */
interface WordContract {

    interface View : BaseContract.View {
        fun speakWord(word: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun onClickedTextToSpeech(word: Word)
    }
}