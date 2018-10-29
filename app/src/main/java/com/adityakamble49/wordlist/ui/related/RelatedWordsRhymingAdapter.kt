package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordRhyming
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Rhyming Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsRhymingAdapter : RelatedWordsCommonAdapter<RelatedWordRhyming>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordRhyming>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordRhyming) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}