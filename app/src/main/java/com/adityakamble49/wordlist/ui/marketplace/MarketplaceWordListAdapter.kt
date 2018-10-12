package com.adityakamble49.wordlist.ui.marketplace

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.utils.inflate
import kotlinx.android.synthetic.main.item_marketplace_word_list.view.*

/**
 * Marketplace WordList Adapter
 *
 * @author Aditya Kamble
 * @since 9/10/2018
 */
class MarketplaceWordListAdapter : RecyclerView.Adapter<MarketplaceWordListAdapter.ViewHolder>() {

    var listOfMarketplaceWordList = mutableListOf<Word>()
    var listDownloadListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_marketplace_word_list) as View)
    }

    override fun getItemCount(): Int {
        return 17
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(position)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            with(itemView) {
                iv_word_list_download.setOnClickListener { listDownloadListener() }
            }
        }
    }
}