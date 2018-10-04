package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.WordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.common.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Main Activity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class MainActivity : BaseInjectableActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainActivityViewModel

    private lateinit var wordListAdapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
    }

    private fun bindView() {
        wordListAdapter = WordListAdapter()
        wordListAdapter.listOfWordList = getWordList()
        val gridLayoutManager = GridLayoutManager(this, 2)
        val itemOffsetDecoration = ItemOffsetDecoration(16)
        rv_word_list.adapter = wordListAdapter
        rv_word_list.layoutManager = gridLayoutManager
        rv_word_list.addItemDecoration(itemOffsetDecoration)
    }

    private fun getWordList(): MutableList<WordList> {
        val list = mutableListOf<WordList>()
        list.add(WordList(1, "", "", "Biology Words"))
        list.add(WordList(2, "", "", "Very Alternatives"))
        list.add(WordList(3, "", "", "GRE Essentials"))
        list.add(WordList(4, "", "", "GRE Advanced"))
        list.add(WordList(5, "", "", "Common 1000"))
        return list
    }
}
