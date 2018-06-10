package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.activity_word_info.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


/**
 * WordView Pager Activity
 *
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordActivity : BaseInjectableActivity(), WordContract.View, View.OnClickListener,
        TextToSpeech.OnInitListener {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: WordContract.Presenter

    // View Fields
    private var optionMenu: Menu? = null

    // Other Fields
    private var currentActivityMode: Int = WordActivityMode.NORMAL.ordinal
    private lateinit var tts: TextToSpeech

    // Constants
    companion object {
        const val IE_KEY_WORD_ID = "word_id"
        const val IE_DEFAULT_WORD_ID = 0
        const val IE_KEY_WORD_ACTIVITY_MODE = "word_activity_mode"
        var IE_DEFAULT_WORD_ACTIVITY_MODE = WordActivityMode.NORMAL.ordinal

        enum class WordActivityMode {
            NORMAL, LEARN, PRACTICE, SINGLE, ADD
        }
    }

    /*
     * Lifecycle Functions
     */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_word_info, menu)
        this.optionMenu = menu
        val submitItem = menu.findItem(R.id.action_submit_word)
        submitItem.isVisible = false
        if (currentActivityMode == WordActivityMode.ADD.ordinal) {
            toggleEditMode(true)
        }
        if (currentActivityMode == WordActivityMode.PRACTICE.ordinal) {
            menu.findItem(R.id.action_edit_word).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        closeTTS()
        super.onDestroy()
    }

    /*
     * Listener Functions
     */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_edit_word -> presenter.onClickEditWord()
            R.id.action_submit_word -> presenter.onClickSubmitWord()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.word_information -> presenter.onClickWordInformation()
            R.id.word_mnemonic -> presenter.onClickWordMnemonic()
            R.id.word_text_to_speech -> presenter.onClickWordTTS()
            R.id.previous_word -> presenter.onPreviousWordAction()
            R.id.fab_dictate -> presenter.onDictateModeAction()
            R.id.next_word -> presenter.onNextWordAction()
        }
    }

    override fun onInit(status: Int) {
        Timber.i(status.toString())
        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {

                override fun onDone(utteranceId: String?) {
                    runOnUiThread { presenter.onTTSDone() }
                }

                override fun onError(utteranceId: String?) {}
                override fun onStart(utteranceId: String?) {}
            })
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

        // Setup Back Action Icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup word info
        word_information.setOnClickListener(this)
        word_mnemonic.setOnClickListener(this)

        // Setup Word TTS
        word_text_to_speech.setOnClickListener(this)

        // Bottom Navigation
        previous_word.setOnClickListener(this)
        fab_dictate.setOnClickListener(this)
        next_word.setOnClickListener(this)
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
        toggleEditMode(false)
        when (currentActivityMode) {
            WordActivityMode.LEARN.ordinal -> toggleWordInfo(true)
            WordActivityMode.PRACTICE.ordinal -> toggleWordInfo(false)
            WordActivityMode.SINGLE.ordinal -> toggleController(false)
            WordActivityMode.ADD.ordinal -> {
                toggleEditMode(true)
                toggleController(false)
            }
        }

        initializeTTS()
    }

    private fun initializeTTS() {
        tts = TextToSpeech(this, this)
        tts.language = Locale.US
    }

    private fun closeTTS() {
        tts.stop()
        tts.shutdown()
    }


    private fun toggleWordInfo(toShow: Boolean) {
        if (toShow) {
            word_information.visible()
            word_mnemonic.visible()
        } else {
            word_information.setText(getString(R.string.tap_to_show))
            word_mnemonic.setText(getString(R.string.tap_to_show))
        }
    }

    private fun toggleController(toShow: Boolean) {
        if (toShow) {
            word_index.visible()
            llBottomNavigation.visible()
            fab_dictate.visible()
        } else {
            word_index.gone()
            llBottomNavigation.gone()
            fab_dictate.gone()
        }
    }

    override fun toggleEditMode(toShow: Boolean) {
        if (toShow) {
            this.optionMenu?.findItem(R.id.action_edit_word)?.isVisible = false
            this.optionMenu?.findItem(R.id.action_submit_word)?.isVisible = true
            word_name.isEnabled = true
            word_type.isEnabled = true
            word_pronunciation.isEnabled = true
            word_information.isEnabled = true
            word_information.isCursorVisible = true
            word_information.background.setColorFilter(
                    ResourcesCompat.getColor(resources, R.color.default_accent, theme),
                    PorterDuff.Mode.SRC_ATOP)
            word_mnemonic.isEnabled = true
            word_mnemonic.isCursorVisible = true
            word_mnemonic.background.setColorFilter(
                    ResourcesCompat.getColor(resources, R.color.default_accent, theme),
                    PorterDuff.Mode.SRC_ATOP)
            word_text_to_speech.gone()
        } else {
            this.optionMenu?.findItem(R.id.action_edit_word)?.isVisible = true
            this.optionMenu?.findItem(R.id.action_submit_word)?.isVisible = false
            word_name.isEnabled = false
            word_type.isEnabled = false
            word_name.background.clearColorFilter()
            word_pronunciation.isEnabled = false
            word_information.isEnabled = false
            word_information.isCursorVisible = false
            word_information.background.clearColorFilter()
            word_mnemonic.isEnabled = false
            word_mnemonic.isCursorVisible = false
            word_mnemonic.background.clearColorFilter()
            word_text_to_speech.visible()
        }
    }

    override fun submitWord() {
        val submittedWord = Word(0, 0, word_name.text.toString(), word_type.text.toString(),
                word_pronunciation.text.toString(), word_information.text.toString(),
                word_mnemonic.text.toString())
        presenter.submitEditedWord(submittedWord)
    }

    override fun submitWordInvalid() {
        Toast.makeText(this, "Word Invalid", Toast.LENGTH_SHORT).show()
    }

    override fun addWordSuccess() {
        Toast.makeText(this, getString(R.string.add_word_success), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun editWordSuccess() {
        Toast.makeText(this, getString(R.string.word_edit_success), Toast.LENGTH_SHORT).show()
        toggleEditMode(false)
    }

    override fun updateWord(word: Word, wordIndex: Int, wordListSize: Int) {
        word_name.setText(word.name)
        word_type.setText(word.type)
        word_pronunciation.setText(word.pronunciation)
        val wordIndex = "$wordIndex/$wordListSize"
        word_index.text = wordIndex
        if (currentActivityMode != WordActivityMode.PRACTICE.ordinal) {
            word_information.setText(word.information)
            word_mnemonic.setText(word.mnemonic)
        } else {
            word_information.setText(getString(R.string.tap_to_show))
            word_mnemonic.setText(getString(R.string.tap_to_show))
        }
    }

    override fun updateWordInformation(information: String) {
        word_information.setText(information)
    }

    override fun updateWordMnemonic(mnemonic: String) {
        word_mnemonic.setText(mnemonic)
    }

    override fun speakWord(name: String) {
        tts.stop()
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(name, TextToSpeech.QUEUE_ADD, null, "")
        } else {
            tts.speak(name, TextToSpeech.QUEUE_ADD, null)
        }
    }

    override fun updateDictateModeSpeed(speed: Float) {
        tts.setSpeechRate(speed)
    }

    override fun stopSpeaking() {
        tts.stop()
    }

    override fun updateWordSingle(word: Word) {
        word_name.setText(word.name)
        word_type.setText(word.type)
        word_pronunciation.setText(word.pronunciation)
        word_information.setText(word.information)
        word_mnemonic.setText(word.mnemonic)
    }

    override fun updateFABDictateIcon(icon: Int) {
        fab_dictate.setImageDrawable(ResourcesCompat.getDrawable(resources, icon, null))
    }
}