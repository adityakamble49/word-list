package com.adityakamble49.wordlist.ui.search

import android.widget.Filter
import android.widget.Filterable
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.FilterResultListener
import com.adityakamble49.wordlist.ui.list.WordListAdapter

/**
 * Search List Adapter
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
class SearchListAdapter : WordListAdapter(), Filterable {

    override var bookMarkItemId: Int = 0
    lateinit var unfilteredItemList: List<Word>
    lateinit var filterResultListener: FilterResultListener<Word>

    override fun getItemCount(): Int {
        if (itemList.size < 20) return itemList.size
        return 20
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint
                if (!charString.isEmpty()) {
                    val localList = mutableListOf<Word>()
                    for (word in unfilteredItemList) {
                        if (word.name.toLowerCase().contains(charString.toString().toLowerCase())) {
                            localList.add(word)
                        }
                    }
                    itemList = localList
                }

                val filteredResult = FilterResults()
                filteredResult.values = itemList
                return filteredResult
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                itemList = results.values as List<Word>
                filterResultListener.onFilteredResult(itemList)
                notifyDataSetChanged()
            }
        }
    }
}