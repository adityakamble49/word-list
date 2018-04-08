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
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
import com.adityakamble49.wordlist.ui.main.MainActivityViewModelFactory
import com.adityakamble49.wordlist.ui.word.WordActivity
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
    @Inject lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory
    @Inject lateinit var presenter: WordListPresenter

    // View Fields
    private lateinit var wordListAdapter: WordListAdapter

    // Other Fields

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val wordId = wordListAdapter.itemList[position].id
        val wordIntent = Intent(activity, WordActivity::class.java)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ID, wordId)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                WordActivity.Companion.WordActivityMode.NORMAL.ordinal)
        startActivity(wordIntent)
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
        lateinit var mainActivityViewModel: MainActivityViewModel
        activity?.let {
            mainActivityViewModel = ViewModelProviders.of(it, mainActivityViewModelFactory)
                    .get(MainActivityViewModel::class.java)
        }
        val wordListViewModel = ViewModelProviders.of(this, wordListViewModelFactory)
                .get(WordListViewModel::class.java)
        presenter.setMainViewModel(mainActivityViewModel)
        presenter.setWordListViewModel(wordListViewModel)
        presenter.initialize()
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