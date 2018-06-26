package com.adityakamble49.wordlist.ui.marketplace

import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.ImageView
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.model.WordListStatus
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
                item_download_image.setImageDrawable(
                        getDrawableByStatus(item_download_image, item.status))
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.item_download_image -> onItemClickListener.onItemClick(null, v,
                        adapterPosition, 0)
            }
        }

    }

    private fun getDrawableByStatus(item_download_image: ImageView, status: String): Drawable? {
        val context = item_download_image.context
        return when (status) {
            WordListStatus.NOT_AVAILABLE.name -> ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_download, context.theme)
            WordListStatus.AVAILABLE.name -> ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_available, context.theme)
            WordListStatus.UPDATE_AVAILABLE.name -> ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_update, context.theme)
            else -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_download,
                    context.theme)
        }
    }
}