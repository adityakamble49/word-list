package com.adityakamble49.wordlist.ui.wordlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.adityakamble49.wordlist.R
import kotlinx.android.synthetic.main.activity_word_list.*
import kotlinx.android.synthetic.main.layout_wordlist_body.*

/**
 * Word List Activity
 *
 * @author Aditya Kamble
 * @since 8/10/2018
 */
class WordListActivity : AppCompatActivity() {

    private lateinit var wordListWordAdapter: WordListWordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        bindView()
    }

    private fun bindView() {
        setupActionBar()
        setupListOfWords()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        // Enable Title only on Collapse
        collapsing_toolbar.title = resources.getString(R.string.word_list_name_default)
        var isShow = true
        var scrollRange = -1
        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsing_toolbar.title = resources.getString(R.string.word_list_name_default)
                isShow = true
            } else if (isShow) {
                collapsing_toolbar.title = ""
                isShow = false
            }
        }
    }

    private fun setupListOfWords() {
        wordListWordAdapter = WordListWordAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecorator = DividerItemDecoration(this, linearLayoutManager.orientation)
        rv_word_list_words.adapter = wordListWordAdapter
        rv_word_list_words.layoutManager = linearLayoutManager
        rv_word_list_words.addItemDecoration(dividerItemDecorator)
    }
}