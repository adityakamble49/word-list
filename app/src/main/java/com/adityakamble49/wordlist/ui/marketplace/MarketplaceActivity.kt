package com.adityakamble49.wordlist.ui.marketplace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MotionEvent
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_marketplace.*

/**
 * Marketplace Activity
 *
 * @author Aditya Kamble
 * @since 9/10/2018
 */
class MarketplaceActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var marketplaceWordListAdapter: MarketplaceWordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        bindView()
    }

    private fun bindView() {
        handleSearchBarEvents()
        setupMarketplaceWordList()
    }

    private fun handleSearchBarEvents() {
        et_search_bar.setOnTouchListener(this)
    }

    private fun setupMarketplaceWordList() {
        marketplaceWordListAdapter = MarketplaceWordListAdapter()
        val gridLayoutManager = GridLayoutManager(this, 2)
        val itemOffsetDecoration = ItemOffsetDecoration(32)
        rv_marketplace_word_list.adapter = marketplaceWordListAdapter
        rv_marketplace_word_list.layoutManager = gridLayoutManager
        rv_marketplace_word_list.addItemDecoration(itemOffsetDecoration)
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
}