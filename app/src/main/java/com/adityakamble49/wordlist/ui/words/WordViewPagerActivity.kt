package com.adityakamble49.wordlist.ui.words

import android.os.Bundle
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseActivity
import com.adityakamble49.wordlist.utils.addFragment

/**
 * WordView Pager Activity
 *
 * @author Aditya Kamble
 * @since 6/4/2018
 */
class WordViewPagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_viewpager)
    }

    override fun bindView() {
        addFragment(WordFragment.newInstance(), R.id.word_page_container)
    }

    override fun initializePresenter() {
    }
}