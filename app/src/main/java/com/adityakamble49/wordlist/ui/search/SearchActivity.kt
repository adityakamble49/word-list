package com.adityakamble49.wordlist.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.ItemOffsetDecoration
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.ui.wordlist.WordListActivity
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Search Activity
 *
 * @author Aditya Kamble
 * @since 12/10/2018
 */
class SearchActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var searchResultWordAdapter: SearchResultWordAdapter
    private lateinit var searchResultWordListAdapter: SearchResultWordListAdapter
    private lateinit var searchResultMarketplaceAdapter: SearchResultMarketplaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        bindView()
    }

    private fun bindView() {
        handleSearchBarEvents()
        setupSearchResultLists()
    }


    private fun handleSearchBarEvents() {
        et_search_bar.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val DRAWABLE_START = 0
        val DRAWABLE_END = 2

        if (event?.action == MotionEvent.ACTION_UP) {
            if (event.rawX > (et_search_bar.right - et_search_bar.compoundDrawables[DRAWABLE_END].bounds.width() - et_search_bar.paddingEnd)) {
                return true
            } else if (event.rawX < (et_search_bar.left + et_search_bar.compoundDrawables[DRAWABLE_START].bounds.width() + et_search_bar.paddingStart)) {
                finish()
                return true
            }
        }

        return false
    }

    private fun setupSearchResultLists() {
        searchResultWordAdapter = SearchResultWordAdapter()
        searchResultWordAdapter.wordAction = {
            startActivity(Intent(this, WordActivity::class.java))
        }
        val wordLinearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecorator = DividerItemDecoration(this, wordLinearLayoutManager.orientation)
        rv_search_word_result.adapter = searchResultWordAdapter
        rv_search_word_result.layoutManager = wordLinearLayoutManager
        rv_search_word_result.addItemDecoration(dividerItemDecorator)
        rv_search_word_result.isNestedScrollingEnabled = false

        searchResultWordListAdapter = SearchResultWordListAdapter()
        searchResultWordListAdapter.wordAction = {
            startActivity(Intent(this, WordListActivity::class.java))
        }
        val wordListLinearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_search_word_list_result.adapter = searchResultWordListAdapter
        val wordListItemOffsetDecoration = ItemOffsetDecoration(16)
        rv_search_word_list_result.layoutManager = wordListLinearLayoutManager
        rv_search_word_list_result.addItemDecoration(wordListItemOffsetDecoration)
        rv_search_word_list_result.isNestedScrollingEnabled = false

        searchResultMarketplaceAdapter = SearchResultMarketplaceAdapter()
        val marketplaceLinearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,
                false)
        rv_search_marketplace_result.adapter = searchResultMarketplaceAdapter
        val marketplaceItemOffsetDecoration = ItemOffsetDecoration(16)
        rv_search_marketplace_result.layoutManager = marketplaceLinearLayoutManager
        rv_search_marketplace_result.addItemDecoration(marketplaceItemOffsetDecoration)
        rv_search_marketplace_result.isNestedScrollingEnabled = false
    }
}