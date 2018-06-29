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
import com.adityakamble49.wordlist.utils.invisible
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
    private var isEditModeLocked = false

    // Constants
    companion object {
        const val IE_KEY_WORD_ID = "word_id"
        const val IE_DEFAULT_WORD_ID = 0
        const val IE_KEY_WORD_ACTIVITY_MODE = "word_activity_mode"
        const val IE_KEY_WORD_TO_ADD = "word_to_add"
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
        menu.findItem(R.id.action_submit_word).isVisible = false
        menu.findItem(R.id.action_show_info).isVisible = false
        lockEditMode(isEditModeLocked)
        if (currentActivityMode == WordActivityMode.ADD.ordinal) {
            toggleEditMode(true)
        }
        if (currentActivityMode == WordActivityMode.SINGLE.ordinal) {
            lockEditMode(true)
        }
        if (currentActivityMode == WordActivityMode.PRACTICE.ordinal) {
            menu.findItem(R.id.action_edit_word).isVisible = false
            menu.findItem(R.id.action_show_info).isVisible = true
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
            R.id.action_show_info -> presenter.onClickShowInfo()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.word_text_to_speech -> presenter.onClickWordTTS()
            R.id.word_mnemonics_sync -> presenter.onClickWordMnemonicsSync(
                    word_name.text.toString())
            R.id.word_info_sync -> presenter.onClickWordInformationSync(word_name.text.toString())
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
        word_info_sync.setOnClickListener(this)
        word_mnemonic.setOnClickListener(this)
        word_mnemonics_sync.setOnClickListener(this)

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

    override fun getActivityPresenter() = presenter

    override fun initializeActivityMode(activityMode: Int) {
        currentActivityMode = activityMode
        toggleEditMode(false)
        when (currentActivityMode) {
            WordActivityMode.LEARN.ordinal -> {
                supportActionBar?.title = getString(R.string.label_learn_mode)
            }
            WordActivityMode.PRACTICE.ordinal -> {
                supportActionBar?.title = getString(R.string.label_practice_mode)
            }
            WordActivityMode.SINGLE.ordinal -> {
                toggleController(false)
            }
            WordActivityMode.ADD.ordinal -> {
                supportActionBar?.title = getString(R.string.label_add_word)
                getWordToAdd()
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

    private fun getWordToAdd() {
        val wordToAdd = intent.getStringExtra(IE_KEY_WORD_TO_ADD)
        wordToAdd?.let {
            word_name.setText(it)
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
            word_information.visible()
            word_information_tv.invisible()
            word_mnemonic.isEnabled = true
            word_mnemonic.isCursorVisible = true
            word_mnemonic.background.setColorFilter(
                    ResourcesCompat.getColor(resources, R.color.default_accent, theme),
                    PorterDuff.Mode.SRC_ATOP)
            word_text_to_speech.gone()
            word_info_sync.visible()
            word_mnemonics_sync.visible()
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
            word_information_tv.visible()
            word_information.invisible()
            word_mnemonic.isEnabled = false
            word_mnemonic.isCursorVisible = false
            word_mnemonic.background.clearColorFilter()
            word_text_to_speech.visible()
            word_info_sync.invisible()
            word_mnemonics_sync.invisible()
        }
    }

    override fun lockEditMode(toLock: Boolean) {
        isEditModeLocked = toLock
        val editMenu = optionMenu?.findItem(R.id.action_edit_word)
        editMenu?.isVisible = !toLock
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

    override fun showEmptyListWarning() {
        Toast.makeText(this, getString(R.string.empty_list_warning), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun updateWord(word: Word, wordIndex: Int, wordListSize: Int) {
        word_name.setText(word.name)
        word_type.setText(word.type)
        word_pronunciation.setText(word.pronunciation)
        val wordIndex = "$wordIndex/$wordListSize"
        word_index.text = wordIndex
        if (currentActivityMode != WordActivityMode.PRACTICE.ordinal) {
            word_information.setText(word.information)
            word_information_tv.text = word.information
            word_mnemonic.setText(word.mnemonic)
        } else {
            word_information.setText("")
            word_information_tv.text = ""
            word_mnemonic.setText("")
        }
    }

    override fun showWordInfo(information: String, mnemonic: String) {
        word_information.setText(information)
        word_information_tv.text = information
        word_mnemonic.setText(mnemonic)
    }

    override fun updateWordInfo(wordInfo: String) {
        word_information.setText(wordInfo)
        word_information_tv.text = wordInfo
    }

    override fun updateMnemonics(mnemonics: String) {
        word_mnemonic.setText(mnemonics)
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
        word_information_tv.text = word.information
        word_mnemonic.setText(word.mnemonic)
    }

    override fun updateFABDictateIcon(icon: Int) {
        fab_dictate.setImageDrawable(ResourcesCompat.getDrawable(resources, icon, null))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMnemonicProgress(toShow: Boolean) {
        if (toShow) {
            word_mnemonics_sync_progress.visible()
        } else {
            word_mnemonics_sync_progress.invisible()
        }
    }

    override fun showWordInfoProgress(toShow: Boolean) {
        if (toShow) {
            word_info_sync_progress.visible()
        } else {
            word_info_sync_progress.invisible()
        }
    }

    override fun scrollToTop() {
        word_scroll_view.scrollTo(0, 0)
    }
}