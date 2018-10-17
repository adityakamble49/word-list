package com.adityakamble49.wordlist.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.utils.inflate

/**
 * Search Result WordList Words Adapter
 *
 * @author Aditya Kamble
 * @since 17/10/2018
 */
class SearchResultWordListAdapter : RecyclerView.Adapter<SearchResultWordListAdapter.ViewHolder>() {

    var listOfWords = mutableListOf<Word>()
    var wordAction: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_word_list_search) as View)
    }

    override fun getItemCount(): Int {
        return 8
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