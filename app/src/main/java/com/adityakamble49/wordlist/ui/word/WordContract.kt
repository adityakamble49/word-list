package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.DrawableRes
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
        fun initializeActivityMode(currentWordActivityMode: Int)
        fun toggleEditMode(toShow: Boolean)
        fun submitWord()
        fun submitWordInvalid()
        fun addWordSuccess()
        fun editWordSuccess()
        fun updateWord(word: Word, wordIndex: Int, wordListSize: Int)
        fun updateWordInformation(information: String)
        fun updateWordMnemonic(mnemonic: String)
        fun speakWord(name: String)
        fun updateDictateModeSpeed(speed: Float)
        fun updateFABDictateIcon(@DrawableRes icon: Int)
        fun stopSpeaking()
        fun updateWordSingle(word: Word)
    }

    interface Presenter : BaseContract.Presenter {
        fun setWordViewModel(wordViewModel: WordViewModel)
        fun setActivityMode(wordActivityMode: Int)
        fun setWordId(wordId: Int)
        fun onPause()
        fun onClickEditWord()
        fun onClickSubmitWord()
        fun submitEditedWord(submittedWord: Word)
        fun onClickWordInformation()
        fun onClickWordMnemonic()
        fun onNextWordAction()
        fun onPreviousWordAction()
        fun onDictateModeAction()
        fun onClickWordTTS()
        fun onTTSDone()
    }
}