package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordTriggered
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Triggered Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsTriggeredAdapter : RelatedWordsCommonAdapter<RelatedWordTriggered>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordTriggered>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordTriggered) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}