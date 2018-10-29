package com.adityakamble49.wordlist.ui.related

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.utils.inflate

/**
 * Related Words Common Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
abstract class RelatedWordsCommonAdapter<T> :
        RecyclerView.Adapter<RelatedWordsCommonAdapter<T>.ViewHolder>() {

    var listOfWord: List<T> = listOf()
    var wordOnClickAction: (relatedWord: T) -> Unit = {}
    var wordLongClickAction: (relatedWord: T) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_related_word) as View
        return getViewHolder(view)
    }

    abstract fun getViewHolder(view: View): RelatedWordsCommonAdapter<T>.ViewHolder

    override fun getItemCount(): Int {
        return listOfWord.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position,
            listOfWord[position])

    abstract inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, item: T) {
            bindView(itemView, item)
            itemView.setOnClickListener { wordOnClickAction(item) }
            itemView.setOnLongClickListener {
                wordLongClickAction(item)
                true
            }
        }

        abstract fun bindView(itemView: View, item: T)
    }
}