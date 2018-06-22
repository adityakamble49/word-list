package com.adityakamble49.wordlist.ui.marketplace

import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.ui.common.SimpleListAdapter
import com.adityakamble49.wordlist.utils.getWordListNameFromFileName
import kotlinx.android.synthetic.main.marketplace_list_item.view.*

/**
 * Marketplace List Adapter
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
open class MarketplaceListAdapter : SimpleListAdapter<MarketplaceWordList>() {

    override fun updateItemList(list: List<MarketplaceWordList>) {
    }

    override fun getItemLayoutId() = R.layout.marketplace_list_item

    override fun createCustomViewHolder(
            view: View): SimpleListAdapter.ViewHolder<MarketplaceWordList> = ViewHolder(view)

    inner class ViewHolder constructor(itemView: View) :
            SimpleListAdapter.ViewHolder<MarketplaceWordList>(itemView) {

        override fun updateBind(position: Int, item: MarketplaceWordList) {
            with(itemView) {
                item_title.text = getWordListNameFromFileName(item.name)
                item_download_image.setOnClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.item_download_image -> onItemClickListener.onItemClick(null, v,
                        adapterPosition, 0)
            }
        }

    }
}