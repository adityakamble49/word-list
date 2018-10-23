package com.adityakamble49.wordlist.ui.related

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.utils.inflate
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Adapter
 *
 * @author Aditya Kamble
 * @since 5/10/2018
 */
class RelatedWordsAdapter : RecyclerView.Adapter<RelatedWordsAdapter.ViewHolder>() {

    var listOfWord: List<RelatedWordBasic> = listOf()
    var wordOnClickAction: (relatedWordBasic: RelatedWordBasic) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_related_word) as View
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfWord.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position,
            listOfWord[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, item: RelatedWordBasic) {
            with(itemView) {
                tv_related_word.text = item.word
            }
            itemView.setOnClickListener { wordOnClickAction(item) }
        }
    }
}