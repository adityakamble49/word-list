package com.adityakamble49.wordlist.ui.word

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_word_image.view.*

/**
 * Word Image Fragment
 *
 * @author Aditya Kamble
 * @since 7/10/2018
 */
class WordImageFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_word_image, container, false)
        bindView(view)
        return view
    }

    companion object {
        fun newInstance() = WordImageFragment()
    }

    private fun bindView(view: View) {
        Glide.with(this)
                .load("http://wordinfo.info/words/images/hedonist-1.jpg")
                .into(view.iv_word_image)
    }
}