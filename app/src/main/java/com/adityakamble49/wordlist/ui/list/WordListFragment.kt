package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.ui.words.WordViewPagerActivity
import com.adityakamble49.wordlist.utils.DataProcessor
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.fragment_wordlist.*
import kotlinx.android.synthetic.main.fragment_wordlist.view.*
import javax.inject.Inject

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListFragment : BaseInjectableFragment(), WordListContract.View,
        AdapterView.OnItemClickListener {

    // Dagger Injected Fields
    @Inject lateinit var wordListViewModelFactory: WordListViewModelFactory
    @Inject lateinit var dataProcessor: DataProcessor

    // View Fields
    private lateinit var wordListAdapter: WordListAdapter

    // Other Fields
    private lateinit var presenter: WordListPresenter

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startActivity(Intent(activity, WordViewPagerActivity::class.java))
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = WordListFragment()
    }

    override fun getLayoutId() = R.layout.fragment_wordlist

    override fun bindView(rootView: View) {
        with(rootView) {

            // Setup Word List
            wordListAdapter = WordListAdapter()
            wordListAdapter.onItemClickListener = this@WordListFragment
            val linearLayoutManager = LinearLayoutManager(context)
            val decorator = DividerItemDecoration(context, linearLayoutManager.orientation)
            recyclerview_word_list.adapter = wordListAdapter
            recyclerview_word_list.layoutManager = linearLayoutManager
            recyclerview_word_list.addItemDecoration(decorator)
        }
    }

    override fun initializePresenter() {
        val viewModel = ViewModelProviders.of(this, wordListViewModelFactory)
                .get(WordListViewModel::class.java)
        presenter = WordListPresenter(this, viewModel)
    }

    override fun showLoading(toShow: Boolean) {
        if (toShow) {
            progress_word_list.visible()
        } else {
            progress_word_list.gone()
        }
    }

    override fun updateWordList(wordList: List<Word>) {
        wordListAdapter.itemList = wordList
        wordListAdapter.notifyDataSetChanged()
    }

    override fun openSingleWord(word: Word) {

    }
}