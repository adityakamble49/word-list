package com.adityakamble49.wordlist.ui.list

import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.SimpleListAdapter
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.word_list_item.view.*

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListAdapter : SimpleListAdapter<Word>() {

    var bookMarkItemId: Int = 1

    override fun updateItemList(list: List<Word>) {
    }

    override fun getItemLayoutId() = R.layout.word_list_item

    override fun createCustomViewHolder(
            view: View): SimpleListAdapter.ViewHolder<Word> = ViewHolder(view)

    inner class ViewHolder constructor(itemView: View) :
            SimpleListAdapter.ViewHolder<Word>(itemView) {

        override fun updateBind(position: Int, item: Word) {
            with(itemView) {
                item_title.text = item.name
                if (item.id == bookMarkItemId) {
                    item_bookmark_image.visible()
                } else {
                    item_bookmark_image.gone()
                }
            }
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(null, v, adapterPosition, 0)
        }

    }
}