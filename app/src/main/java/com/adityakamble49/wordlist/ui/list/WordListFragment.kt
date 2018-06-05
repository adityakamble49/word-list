package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import com.afollestad.materialdialogs.MaterialDialog
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
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: WordListContract.Presenter

    // View Fields
    private lateinit var wordListAdapter: WordListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // Other Fields
    private lateinit var savedWordLists: List<WordList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_wordlist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_load_list -> presenter.onClickLoadList()
        }
        return super.onOptionsItemSelected(item)
    }

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

    override fun bindViewOnCreate() {
        linearLayoutManager = LinearLayoutManager(context)
    }

    override fun bindView() {
        with(rootView) {

            // Setup Word List
            wordListAdapter = WordListAdapter()
            wordListAdapter.onItemClickListener = this@WordListFragment
            val decorator = DividerItemDecoration(context, linearLayoutManager.orientation)
            recyclerview_word_list.adapter = wordListAdapter
            recyclerview_word_list.layoutManager = linearLayoutManager
            recyclerview_word_list.addItemDecoration(decorator)
        }
    }

    override fun initializePresenter() {
        val wordListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WordListViewModel::class.java)
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

    override fun updateSavedWordLists(savedWordLists: List<WordList>) {
        this.savedWordLists = savedWordLists
    }

    override fun showLoadSavedListDialog() {
        val wordListNames = mutableListOf<String>()
        savedWordLists.forEach { wordList -> wordListNames.add(wordList.name) }
        context?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.title_load_saved_list)
                    .items(wordListNames)
                    .itemsCallback { _, _, which, _ ->
                        presenter.onClickSavedListItem(savedWordLists[which])
                    }.build().show()
        }
    }

    override fun updateWords(wordList: List<Word>) {
        wordListAdapter.itemList = wordList
        wordListAdapter.notifyDataSetChanged()
    }

    override fun openSingleWord(word: Word) {

    }

    override fun updateBookmarkItem(bookmarkItemId: Int) {
        wordListAdapter.bookMarkItemId = bookmarkItemId
        wordListAdapter.notifyDataSetChanged()
    }
}