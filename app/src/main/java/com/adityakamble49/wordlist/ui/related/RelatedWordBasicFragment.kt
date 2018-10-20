package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.fragment_related_words.*
import kotlinx.android.synthetic.main.fragment_related_words.view.*
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
    @Inject lateinit var relatedWordBasicViewModel: RelatedWordBasicViewModel
    @Inject lateinit var relatedWordViewModel: RelatedWordsViewModel

    private lateinit var relatedWordsAdapter: RelatedWordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_related_words, container, false)
        bindView(view)
        return view
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = RelatedWordBasicFragment()
    }

    private fun setupViewModel() {
        relatedWordViewModel = ViewModelProviders.of(activity as FragmentActivity, viewModelFactory)
                .get(RelatedWordsViewModel::class.java)
        relatedWordBasicViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordBasicViewModel::class.java)
    }

    private fun bindView(view: View) {
        with(view) {
            relatedWordsAdapter = RelatedWordsAdapter()
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(getSpanCount(85),
                    StaggeredGridLayoutManager.HORIZONTAL)
            rv_means_like_words.adapter = relatedWordsAdapter
            rv_means_like_words.layoutManager = staggeredGridLayoutManager
        }

        relatedWordViewModel.searchQuery.observe(this, Observer<String> {
            it?.let {
                relatedWordBasicViewModel.searchPublishSubject.onNext(it)
            }
        })

        relatedWordBasicViewModel.relatedWordBasicList.observe(this,
                Observer<List<RelatedWordBasic>> {
                    it?.let {
                        pb_loading.gone()
                        rv_means_like_words.visible()
                        relatedWordsAdapter.listOfWord = it
                        relatedWordsAdapter.notifyDataSetChanged()
                    }
                })
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