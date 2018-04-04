package com.adityakamble49.wordlist.ui.list

import android.view.View
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.SimpleListAdapter

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListAdapter : SimpleListAdapter<Word>() {

    override fun updateItemList(list: List<Word>) {
    }

    override fun createCustomViewHolder(
            view: View): SimpleListAdapter.ViewHolder<Word> = ViewHolder(
            view)

    class ViewHolder constructor(itemView: View) : SimpleListAdapter.ViewHolder<Word>(itemView) {

        override fun updateBind(position: Int, item: Word) {
        }

        override fun onClick(v: View?) {
        }

    }
}