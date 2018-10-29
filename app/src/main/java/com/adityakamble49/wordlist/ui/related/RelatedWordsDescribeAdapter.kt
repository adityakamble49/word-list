package com.adityakamble49.wordlist.ui.related

import android.view.View
import com.adityakamble49.wordlist.model.RelatedWordDescribe
import kotlinx.android.synthetic.main.item_related_word.view.*

/**
 * Related Words Describe Adapter
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsDescribeAdapter : RelatedWordsCommonAdapter<RelatedWordDescribe>() {

    override fun getViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(itemView: View) :
            RelatedWordsCommonAdapter<RelatedWordDescribe>.ViewHolder(itemView) {

        override fun bindView(itemView: View, item: RelatedWordDescribe) {
            with(itemView) {
                tv_related_word.text = item.word
            }
        }
    }
}