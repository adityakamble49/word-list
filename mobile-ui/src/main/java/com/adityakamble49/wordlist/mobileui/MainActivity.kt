package com.adityakamble49.wordlist.mobileui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.wordlist.mobileui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.presentation.state.ResourceState
import com.adityakamble49.wordlist.presentation.wordlist.WordListViewModel
import kotlinx.android.synthetic.main.activity_wordlist.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseInjectableActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WordListViewModel
    private lateinit var adapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordlist)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                WordListViewModel::class.java)

        bindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> viewModel.addWordList(WordListDataFactory.randomUUID())
        }
        return true
    }

    private fun bindView() {

        adapter = WordListAdapter()
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rv_wordlist.adapter = adapter
        rv_wordlist.layoutManager = layoutManager
        rv_wordlist.addItemDecoration(dividerItemDecoration)

        viewModel.getWordLists().observe(this, Observer {
            Timber.i("${it?.state} - ${it?.data} - ${it?.message}")
            when (it?.state) {
                ResourceState.LOADING -> {
                    progress_word_list.visibility = View.VISIBLE
                }
                ResourceState.SUCCESS -> {
                    progress_word_list.visibility = View.GONE
                    it.data?.let {
                        adapter.itemList = it
                        adapter.notifyDataSetChanged()
                    }
                }
                ResourceState.ERROR -> {
                    progress_word_list.visibility = View.GONE
                    it.message?.let { error_message.text = it }
                }
            }
        })
    }
}