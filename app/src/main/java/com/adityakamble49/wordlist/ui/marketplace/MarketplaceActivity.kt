package com.adityakamble49.wordlist.ui.marketplace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_marketplace.*

/**
 * Marketplace Activity
 *
 * @author Aditya Kamble
 * @since 9/10/2018
 */
class MarketplaceActivity : AppCompatActivity() {

    private lateinit var marketplaceWordListAdapter: MarketplaceWordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        bindView()
    }

    private fun bindView() {
        setupMarketplaceWordList()
    }

    private fun setupMarketplaceWordList() {
        marketplaceWordListAdapter = MarketplaceWordListAdapter()
        val gridLayoutManager = GridLayoutManager(this, 2)
        val itemOffsetDecoration = ItemOffsetDecoration(32)
        rv_marketplace_word_list.adapter = marketplaceWordListAdapter
        rv_marketplace_word_list.layoutManager = gridLayoutManager
        rv_marketplace_word_list.addItemDecoration(itemOffsetDecoration)
    }
}