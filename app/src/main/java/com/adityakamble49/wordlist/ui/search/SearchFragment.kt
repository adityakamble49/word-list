package com.adityakamble49.wordlist.ui.search

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.utils.invisible
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject

/**
 * Search Fragment
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
class SearchFragment : BaseInjectableFragment(), SearchContract.View,
        AdapterView.OnItemClickListener, View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: SearchContract.Presenter

    // View Fields
    private lateinit var handler: Handler
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var searchView: SearchView

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        handler = Handler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        handleSearch(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_word_alert_layout -> presenter.onClickAddWordAlert()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val wordId = searchListAdapter.itemList[position].id
        val wordIntent = Intent(activity, WordActivity::class.java)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ID, wordId)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                WordActivity.Companion.WordActivityMode.SINGLE.ordinal)
        startActivity(wordIntent)
        closeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_search

    override fun bindViewOnCreate() {}

    override fun bindView() {
        with(rootView) {

            // Setup Search Result Recycler View
            searchListAdapter = SearchListAdapter()
            searchListAdapter.onItemClickListener = this@SearchFragment
            val linearLayoutManager = LinearLayoutManager(context)
            val decorator = DividerItemDecoration(context, linearLayoutManager.orientation)
            recyclerview_search_result.layoutManager = linearLayoutManager
            recyclerview_search_result.adapter = searchListAdapter
            recyclerview_search_result.addItemDecoration(decorator)

            // Setup Add word Alert
            add_word_alert_layout.setOnClickListener(this@SearchFragment)
        }
    }

    override fun initializePresenter() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)
        presenter.setViewModel(viewModel)
        presenter.initialize()
    }

    override fun getFragmentPresenter() = presenter

    override fun updateAllWordList(wordList: List<Word>) {
        searchListAdapter.itemList = wordList
        searchListAdapter.unfilteredItemList = wordList
        searchListAdapter.notifyDataSetChanged()
    }

    override fun toggleAddWordAlert(toShow: Boolean) {
        if (toShow) {
            rootView.add_word_alert_layout.visible()
        } else {
            rootView.add_word_alert_layout.invisible()
        }
    }

    override fun openAddWordUI() {
        val wordIntent = Intent(activity, WordActivity::class.java)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                WordActivity.Companion.WordActivityMode.ADD.ordinal)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_TO_ADD, searchView.query.toString())
        startActivity(wordIntent)
        closeFragment()
    }

    private fun handleSearch(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search_all)
        searchView = searchItem.actionView as SearchView
        searchView.isIconified = false
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnCloseListener {
            searchView.onActionViewCollapsed()
            closeFragment()
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.postDelayed({
                    searchListAdapter.filter.filter(newText)
                }, 500)
                return true
            }
        })
    }

    private fun closeFragment() {
        activity?.onBackPressed()
    }
}