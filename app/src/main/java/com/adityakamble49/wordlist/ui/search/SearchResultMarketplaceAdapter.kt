package com.adityakamble49.wordlist.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.utils.inflate
import kotlinx.android.synthetic.main.item_marketplace_search.view.*

/**
 * Search Result WordList Words Adapter
 *
 * @author Aditya Kamble
 * @since 17/10/2018
 */
class SearchResultMarketplaceAdapter : RecyclerView.Adapter<SearchResultMarketplaceAdapter.ViewHolder>() {

    var listOfWords = mutableListOf<Word>()
    var listDownloadListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_marketplace_search) as View)
    }

    override fun getItemCount(): Int {
        return 8
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