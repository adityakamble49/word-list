package com.adityakamble49.wordlist.ui.words

import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseFragment

/**
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordFragment : BaseFragment() {

    companion object {
        fun newInstance() = WordFragment()
    }

    override fun getLayoutId() = R.layout.fragment_word_info

    override fun bindView(rootView: View) {
    }

    override fun initializePresenter() {
    }

}