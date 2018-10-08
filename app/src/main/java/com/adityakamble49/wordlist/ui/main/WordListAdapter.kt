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
    var addWordListAction: () -> Unit = {}
    var wordListAction: (wordList: WordList) -> Unit = {}

    init {
        listOfWordList.add(WordList(0, "", "", ""))
    }

    companion object {
        private val VIEW_TYPE_ADD_WORDLIST = 1
        private val VIEW_TYPE_WORDLIST = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD_WORDLIST -> {
                val view = parent.inflate(R.layout.item_word_list_add) as View
                view.setOnClickListener { addWordListAction() }
                return ViewHolder(view)
            }
            VIEW_TYPE_WORDLIST -> ViewHolder(parent.inflate(R.layout.item_word_list) as View)
            else -> ViewHolder(parent.inflate(R.layout.item_word_list) as View)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_ADD_WORDLIST
        } else {
            VIEW_TYPE_WORDLIST
        }
    }

    override fun getItemCount(): Int {
        return listOfWordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordList = listOfWordList[position]
        when (holder.itemViewType) {
            VIEW_TYPE_WORDLIST -> holder.bind(position, wordList)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, item: WordList) {
            with(itemView) {
                tv_word_list_name.text = item.name
            }
            itemView.setOnClickListener {
                wordListAction(item)
            }
        }
    }
}