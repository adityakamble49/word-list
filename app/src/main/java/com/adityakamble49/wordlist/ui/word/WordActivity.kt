package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.activity_word_info.*
import javax.inject.Inject

/**
 * WordView Pager Activity
 *
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordActivity : BaseInjectableActivity(), WordContract.View, View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: WordContract.Presenter

    // Other Fields
    private var currentActivityMode: Int = WordActivityMode.NORMAL.ordinal

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

    /*
     * Lifecycle Functions
     */

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_word, menu)
        return true
    }

    /*
     * Listener Functions
     */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_next_word -> presenter.onNextWordAction()
            R.id.action_previous_word -> presenter.onPreviousWordAction()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.word_information -> presenter.onClickWordInformation()
            R.id.word_mnemonic -> presenter.onClickWordMnemonic()
        }
    }


    /*
     * Helper Functions
     */

    override fun getLayoutId() = R.layout.activity_word_info

    private fun getWordId() = intent.getIntExtra(IE_KEY_WORD_ID, IE_DEFAULT_WORD_ID)

    private fun getWordActivityMode() =
            intent.getIntExtra(IE_KEY_WORD_ACTIVITY_MODE, IE_DEFAULT_WORD_ACTIVITY_MODE)

    override fun bindView() {

        // Setup word info
        word_information.setOnClickListener(this)
        word_mnemonic.setOnClickListener(this)
    }

    override fun initializePresenter() {
        presenter.setWordViewModel(ViewModelProviders.of(this, viewModelFactory)
                .get(WordViewModel::class.java))
        presenter.setActivityMode(getWordActivityMode())
        presenter.setWordId(getWordId())
        presenter.initialize()
    }

    override fun initializeActivityMode(activityMode: Int) {
        currentActivityMode = activityMode
        when (currentActivityMode) {
            WordActivityMode.LEARN.ordinal -> toggleWordInfo(true)
            WordActivityMode.PRACTICE.ordinal -> toggleWordInfo(false)
        }
    }


    private fun toggleWordInfo(toShow: Boolean) {
        if (toShow) {
            word_information.visible()
            word_mnemonic.visible()
        } else {
            word_information.text = "Tap to View"
            word_mnemonic.text = "Tap to View"
        }
    }

    override fun updateWord(word: Word) {
        word_name.text = word.name
        word_type.text = word.type
        word_pronunciation.text = word.pronunciation
        if (currentActivityMode != WordActivityMode.PRACTICE.ordinal) {
            word_information.text = word.information
            word_mnemonic.text = word.mnemonic
        } else {
            word_information.text = "Tap to Show"
            word_mnemonic.text = "Tap to Show"
        }
    }

    override fun updateWordInformation(information: String) {
        word_information.text = information
    }

    override fun updateWordMnemonic(mnemonic: String) {
        word_mnemonic.text = mnemonic
    }
}