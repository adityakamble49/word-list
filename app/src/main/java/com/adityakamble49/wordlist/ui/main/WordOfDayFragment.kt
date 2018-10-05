package com.adityakamble49.wordlist.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R

/**
 * Word of Day Fragment
 *
 * @author Aditya Kamble
 * @since 5/10/2018
 */
class WordOfDayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_card_word_of_day, container, false)
        bindView(view)
        return view
    }

    companion object {
        fun newInstance() = WordOfDayFragment()
    }

    private fun bindView(view: View) {

    }
}