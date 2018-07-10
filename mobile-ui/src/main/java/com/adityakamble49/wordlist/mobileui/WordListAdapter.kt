package com.adityakamble49.wordlist.mobileui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.presentation.model.WordListView
import kotlinx.android.synthetic.main.word_l_item.view.*

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    var itemList = listOf<WordListView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_l_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
            itemList[position], position)

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: WordListView, position: Int) {
            with(itemView) {
                itemView.title.text = item.name
            }
        }
    }
}