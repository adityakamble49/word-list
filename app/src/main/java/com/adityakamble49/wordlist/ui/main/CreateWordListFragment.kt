package com.adityakamble49.wordlist.ui.main

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import kotlinx.android.synthetic.main.fragment_create_wordlist.view.*
import kotlinx.android.synthetic.main.layout_create_wordlist_header.view.*

/**
 * Create Word List Fragment
 *
 * @author Aditya Kamble
 * @since 6/10/2018
 */
class CreateWordListFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_NoActionBar)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_wordlist, container, false)
        bindView(view)
        return view
    }

    companion object {
        fun newInstance() = CreateWordListFragment()
    }

    private fun bindView(view: View) {
        with(view) {
            iv_create_word_list_close.setOnClickListener { dialog.dismiss() }
            fab_create_word_list.setOnClickListener { dialog.dismiss() }
        }
    }
}