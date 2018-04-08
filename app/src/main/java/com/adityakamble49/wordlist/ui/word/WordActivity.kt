package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.utils.Constants.WordViewPager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_info)
    }

    override fun bindView() {
    }

    override fun initializePresenter() {
        presenter.wordViewModel = ViewModelProviders.of(this, wordViewModelFactory)
                .get(WordViewModel::class.java)
        presenter.updateWordId(getIntentExtraWordId())
        presenter.initialize()
    }

    private fun getIntentExtraWordId() =
            intent.getIntExtra(WordViewPager.IE_KEY_WORD_ID, WordViewPager.IE_DEFAULT_WORD_ID)

    override fun updateWord(word: Word) {
        word_name.text = word.name
        word_type.text = word.type
        word_pronunciation.text = word.pronunciation
        word_information.text = word.information
        word_mnemonic.text = word.mnemonic
    }
}