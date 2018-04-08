package com.adityakamble49.wordlist.ui.words

import android.speech.tts.TextToSpeech
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_word_info.view.*
import java.util.*

/**
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordFragment : BaseFragment(), WordContract.View, TextToSpeech.OnInitListener,
        View.OnClickListener {

    private lateinit var presenter: WordPresenter

    override fun onInit(status: Int) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.word_text_to_speech -> presenter.onClickedTextToSpeech(
                    Word(1, 1, "", "", "", "", ""))
        }
    }

    companion object {
        fun newInstance() = WordFragment()
    }

    override fun getLayoutId() = R.layout.fragment_word_info

    override fun bindView(rootView: View) {
        rootView.word_text_to_speech.setOnClickListener(this)
    }

    override fun initializePresenter() {
        presenter = WordPresenter(this)
    }

    override fun speakWord(word: String) {
        val tts = TextToSpeech(context, this)
        tts.language = Locale.US
        tts.speak(word, TextToSpeech.QUEUE_ADD, null)
    }
}