package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordAntonym
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Antonym Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAntonymAdapter : RelatedWordsCommonAdapter<RelatedWordAntonym>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordAntonym>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordAntonym) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}