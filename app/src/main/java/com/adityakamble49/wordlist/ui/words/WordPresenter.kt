package com.adityakamble49.wordlist.ui.words

import com.adityakamble49.wordlist.model.Word

/**
 * @author Aditya Kamble
 * @since 7/4/2018
 */
class WordPresenter constructor(private val view: WordContract.View) : WordContract.Presenter {

    override fun onClickedTextToSpeech(word: Word) {
        view.speakWord(word.name)
    }
}