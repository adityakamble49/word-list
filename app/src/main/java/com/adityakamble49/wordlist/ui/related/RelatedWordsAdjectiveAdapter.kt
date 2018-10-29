package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordAdjective
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Adjective Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAdjectiveAdapter : RelatedWordsCommonAdapter<RelatedWordAdjective>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordAdjective>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordAdjective) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}