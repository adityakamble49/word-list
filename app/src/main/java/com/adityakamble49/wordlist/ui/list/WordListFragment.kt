package com.adityakamble49.wordlist.ui.list

import android.Manifest
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.ui.search.SearchFragment
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.hasSpecialChar
import com.adityakamble49.wordlist.utils.replaceFragment
import com.adityakamble49.wordlist.utils.visible
import com.afollestad.materialdialogs.DialogAction
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
    private var addWordMenuItem: MenuItem? = null

    // Other Fields
    private lateinit var savedWordLists: List<WordList>
    private var disableAddWordMenu = false
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val RC_PERMISSIONS = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (!arePermissionGranted()) {
            askRequiredPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.requestUpdateWordList()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_wordlist, menu)
        addWordMenuItem = menu?.findItem(R.id.action_add_word)
        if (disableAddWordMenu) {
            addWordMenuItem?.isVisible = false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> presenter.onClickSearch()
            R.id.action_load_list -> presenter.onClickLoadList()
            R.id.action_create_list -> presenter.onClickCreateList()
            R.id.action_import_list -> presenter.onClickImportList()
            R.id.action_export_list -> presenter.onClickExportList()
            R.id.action_add_word -> presenter.onClickAddWord()
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

    override fun bindViewOnCreate() {}

    override fun bindView() {
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
        val wordListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WordListViewModel::class.java)
        presenter.setWordListViewModel(wordListViewModel)
        presenter.initialize()
    }

    override fun getFragmentPresenter() = presenter

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

    override fun updateCurrentWordListName(wordListName: String) {
        (activity as AppCompatActivity).supportActionBar?.title = wordListName
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

    override fun showCreateListDialog() {
        context?.let {
            MaterialDialog.Builder(it)
                    .title(getString(R.string.create_word_list_dialog_title))
                    .content(getString(R.string.create_word_list_dialog_content))
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .inputRange(1, 50)
                    .input(getString(R.string.create_word_list_dialog_hint), "", { dialog, input ->
                        val keyName = input.toString()
                        if (keyName.hasSpecialChar()) {
                            dialog.getActionButton(DialogAction.POSITIVE).isEnabled = false
                            dialog.setContent(getString(
                                    R.string.create_word_list_dialog_special_char_warning))
                        } else if (keyName.isEmpty()) {
                            dialog.getActionButton(DialogAction.POSITIVE).isEnabled = false
                            dialog.setContent(getString(R.string.create_word_list_dialog_content))
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).isEnabled = true
                            dialog.setContent(getString(R.string.create_word_list_dialog_content))
                        }
                    })
                    .onPositive { dialog, _ ->
                        dialog.inputEditText?.text.let {
                            presenter.onCreateWordListPositive(it.toString())
                        }
                    }
                    .alwaysCallInputCallback()
                    .build().show()
        }
    }

    private fun arePermissionGranted(): Boolean {
        context?.let {
            return !(ContextCompat.checkSelfPermission(it,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(it,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        }
        return false
    }

    private fun askRequiredPermissions() {
        if (!arePermissionGranted()) {
            activity?.let {
                ActivityCompat.requestPermissions(it, PERMISSIONS_REQUIRED, RC_PERMISSIONS)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            RC_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun showMessage(response: String) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
    }

    override fun openAddWordUI() {
        val wordIntent = Intent(activity, WordActivity::class.java)
        wordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                WordActivity.Companion.WordActivityMode.ADD.ordinal)
        startActivity(wordIntent)
    }

    override fun updateWords(wordList: List<Word>) {
        toggleEmptyView(wordList.size)
        wordListAdapter.itemList = wordList
        wordListAdapter.notifyDataSetChanged()
    }

    private fun toggleEmptyView(size: Int) {
        if (size > 0) {
            ll_empty_view.gone()
        } else {
            ll_empty_view.visible()
        }
    }

    override fun openSearch() {
        (activity as AppCompatActivity).replaceFragment(SearchFragment.newInstance(),
                R.id.main_container)
    }

    override fun openSingleWord(word: Word) {

    }

    override fun updateBookmarkItem(bookmarkItemId: Int) {
        wordListAdapter.bookMarkItemId = bookmarkItemId
        wordListAdapter.notifyDataSetChanged()
    }

    override fun updateMenus(wordList: WordList) {
        if (!wordList.marketplaceFilename.isEmpty()) {
            disableAddWordMenu = true
            addWordMenuItem?.isVisible = false
        } else {
            disableAddWordMenu = false
            addWordMenuItem?.isVisible = true
        }
    }
}