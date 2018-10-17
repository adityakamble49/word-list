package com.adityakamble49.wordlist.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.utils.inflate

/**
 * Search Result Word Adapter
 *
 * @author Aditya Kamble
 * @since 17/10/2018
 */
class SearchResultWordAdapter : RecyclerView.Adapter<SearchResultWordAdapter.ViewHolder>() {

    var listOfWords = mutableListOf<Word>()
    var wordAction: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_word_list_word) as View)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(position)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView?.setOnClickListener {
                wordAction()
            }
        }
    }
}