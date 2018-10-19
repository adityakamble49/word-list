package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import kotlinx.android.synthetic.main.fragment_means_like.view.*
import java.util.*
import javax.inject.Inject


/**
 * Means Like Fragment
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class RelatedWordBasicFragment : BaseInjectableFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: RelatedWordBasicViewModel

    private lateinit var relatedWordsAdapter: RelatedWordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_means_like, container, false)
        bindView(view)
        return view
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = RelatedWordBasicFragment()
    }

    private fun bindView(view: View) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordBasicViewModel::class.java)
        with(view) {
            relatedWordsAdapter = RelatedWordsAdapter()
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(getSpanCount(85),
                    StaggeredGridLayoutManager.HORIZONTAL)
            rv_means_like_words.adapter = relatedWordsAdapter
            rv_means_like_words.layoutManager = staggeredGridLayoutManager

            viewModel.relatedWordBasicList.observe(this@RelatedWordBasicFragment,
                    Observer<List<RelatedWordBasic>> {
                        it?.let {
                            relatedWordsAdapter.listOfWord = it
                            relatedWordsAdapter.notifyDataSetChanged()
                        }
                    })
        }
    }

    private fun getWordString(random: Random, length: Int): String {
        val input = "abcdefghijklmnopqrstuvwxyz"
        val sb = StringBuilder()
        repeat(length) {
            sb.append(input[random.nextInt(26)])
        }
        return sb.toString()
    }

    private fun getSpanCount(itemHeight: Int): Int {
        val displayMetrics = (context as Context).resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        return (dpHeight / itemHeight).toInt()
    }
}