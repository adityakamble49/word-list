package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Status
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.activity_related_words.*
import kotlinx.android.synthetic.main.fragment_related_words.*
import kotlinx.android.synthetic.main.fragment_related_words.view.*
import javax.inject.Inject


/**
 * Related Word Common Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
abstract class RelatedWordsCommonFragment<T> : BaseInjectableFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var relatedWordBasicViewModel: RelatedWordsCommonViewModel<T>
    lateinit var relatedWordViewModel: RelatedWordsViewModel

    private lateinit var relatedWordsAdapter: RelatedWordsCommonAdapter<T>

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

    private fun setupViewModel() {
        relatedWordViewModel = ViewModelProviders.of(activity as FragmentActivity, viewModelFactory)
                .get(RelatedWordsViewModel::class.java)
        relatedWordBasicViewModel = getRelatedWordCommonViewModel()
    }

    private fun bindView(view: View) {
        with(view) {
            relatedWordsAdapter = getRelatedWordCommonAdapter()
            relatedWordsAdapter.wordOnClickAction = { openWordInfo(it) }
            relatedWordsAdapter.wordLongClickAction = { searchRelatedWords(it) }
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

        relatedWordBasicViewModel.requestStatus.observe(this, Observer<Status> {
            it?.let {
                when (it) {
                    Status.RUNNING -> {
                        pb_loading.visible()
                        rv_means_like_words.gone()
                    }
                    Status.SUCCESS -> {
                        pb_loading.gone()
                        rv_means_like_words.visible()
                    }
                }
            }
        })

        relatedWordBasicViewModel.relatedWordList.observe(this,
                Observer<List<T>> {
                    it?.let {
                        relatedWordsAdapter.listOfWord = it
                        relatedWordsAdapter.notifyDataSetChanged()
                    }
                })
    }

    private fun getSpanCount(itemHeight: Int): Int {
        val displayMetrics = (context as Context).resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        return (dpHeight / itemHeight).toInt()
    }

    private fun openWordInfo(relatedWord: T) {
        val wordActivityIntent = Intent(activity, WordActivity::class.java)
        wordActivityIntent.putExtra(WordActivity.WORD_NAME, getCurrentWordName(relatedWord))
        startActivity(wordActivityIntent)
    }

    private fun searchRelatedWords(relatedWord: T) {
        (activity as RelatedWordsActivity).et_search_bar.setText(getCurrentWordName(relatedWord))
    }

    protected abstract fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<T>

    protected abstract fun getRelatedWordCommonAdapter(): RelatedWordsCommonAdapter<T>

    protected abstract fun getCurrentWordName(relatedWord: T): String
}