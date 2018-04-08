package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.activity_word_info.*
import javax.inject.Inject

/**
 * WordView Pager Activity
 *
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordActivity : BaseInjectableActivity(), WordContract.View {

    // Dagger Injected Fields
    @Inject lateinit var wordViewModelFactory: WordViewModelFactory
    @Inject lateinit var presenter: WordPresenter

    // Constants
    companion object {
        const val IE_KEY_WORD_ID = "word_id"
        const val IE_DEFAULT_WORD_ID = 0
        const val IE_KEY_WORD_ACTIVITY_MODE = "word_activity_mode"
        var IE_DEFAULT_WORD_ACTIVITY_MODE = WordActivityMode.NORMAL.ordinal

        enum class WordActivityMode {
            NORMAL, LEARN, PRACTICE
        }
    }

    override fun getLayoutId() = R.layout.activity_word_info

    override fun bindView() {
    }

    override fun initializePresenter() {
        presenter.wordViewModel = ViewModelProviders.of(this, wordViewModelFactory)
                .get(WordViewModel::class.java)
        presenter.initialize()
    }

    override fun initializeActivityMode() {
        presenter.loadWord(getWordId())
        when (getWordActivityMode()) {
            WordActivityMode.LEARN.ordinal -> toggleWordInfo(true)
            WordActivityMode.PRACTICE.ordinal -> toggleWordInfo(false)
        }
    }


    private fun toggleWordInfo(toShow: Boolean) {
        if (toShow) {
            word_information.visible()
            word_mnemonic.visible()
        } else {
            word_information.gone()
            word_mnemonic.gone()
        }
    }

    private fun getWordId() = intent.getIntExtra(IE_KEY_WORD_ID, IE_DEFAULT_WORD_ID)

    private fun getWordActivityMode() =
            intent.getIntExtra(IE_KEY_WORD_ACTIVITY_MODE, IE_DEFAULT_WORD_ACTIVITY_MODE)

    override fun updateWord(word: Word) {
        word_name.text = word.name
        word_type.text = word.type
        word_pronunciation.text = word.pronunciation
        word_information.text = word.information
        word_mnemonic.text = word.mnemonic
    }
}