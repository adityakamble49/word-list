package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.content.res.ResourcesCompat
import android.view.MenuItem
import android.view.View
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
            NORMAL, LEARN, PRACTICE, SINGLE
        }
    }

    /*
     * Lifecycle Functions
     */

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
        when (currentActivityMode) {
            WordActivityMode.LEARN.ordinal -> toggleWordInfo(true)
            WordActivityMode.PRACTICE.ordinal -> toggleWordInfo(false)
            WordActivityMode.SINGLE.ordinal -> toggleController(false)
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
            word_information.text = "Tap to View"
            word_mnemonic.text = "Tap to View"
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

    override fun updateWord(word: Word, wordIndex: Int, wordListSize: Int) {
        word_name.text = word.name
        word_type.text = word.type
        word_pronunciation.text = word.pronunciation
        val wordIndex = "$wordIndex/$wordListSize"
        word_index.text = wordIndex
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
        word_name.text = word.name
        word_type.text = word.type
        word_pronunciation.text = word.pronunciation
        word_information.text = word.information
        word_mnemonic.text = word.mnemonic
    }

    override fun updateFABDictateIcon(icon: Int) {
        fab_dictate.setImageDrawable(ResourcesCompat.getDrawable(resources, icon, null))
    }
}