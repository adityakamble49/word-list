package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordBasic
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Adapter
 *
 * @author Aditya Kamble
 * @since 5/10/2018
 */
class RelatedWordsAdapter : RelatedWordsCommonAdapter<RelatedWordBasic>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordBasic>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordBasic) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}