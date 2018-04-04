package com.adityakamble49.wordlist.ui.common

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.utils.inflate

/**
 * @author Aditya Kamble
 * @since 20/3/2018
 */
abstract class SimpleListAdapter<T> : RecyclerView.Adapter<SimpleListAdapter.ViewHolder<T>>() {

    var itemList: List<T> = mutableListOf()
    lateinit var onItemClickListener: AdapterView.OnItemClickListener

    abstract fun updateItemList(list: List<T>)

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(position,
            itemList[position])

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T>? {
        val view = parent.inflate(R.layout.simple_list_item)
        view?.let { return createCustomViewHolder(it) }
        return null
    }

    abstract fun createCustomViewHolder(view: View): ViewHolder<T>

    abstract class ViewHolder<T> constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        fun bind(position: Int, item: T) {
            itemView.setOnClickListener(this)
            updateBind(position, item)
        }

        abstract fun updateBind(position: Int, item: T)
    }
}