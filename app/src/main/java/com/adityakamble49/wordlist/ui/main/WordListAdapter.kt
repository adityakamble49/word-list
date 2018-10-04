package com.adityakamble49.wordlist.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.WordList
import com.adityakamble49.wordlist.utils.inflate
import kotlinx.android.synthetic.main.item_word_list.view.*

/**
 * WordList Adapter
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    var listOfWordList = mutableListOf<WordList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_word_list)
        return ViewHolder(view as View)
    }

    override fun getItemCount(): Int {
        return listOfWordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(position, listOfWordList[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, item: WordList) {
            with(itemView) {
                tv_word_list_name.text = item.name
            }
        }
    }
}