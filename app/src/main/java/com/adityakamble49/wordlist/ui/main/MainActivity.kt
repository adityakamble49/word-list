package com.adityakamble49.wordlist.ui.main

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.list.WordListFragment
import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.utils.addFragment
import com.adityakamble49.wordlist.utils.showToast
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseInjectableActivity(), MainContract.View, View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: MainActivityViewModelFactory
    @Inject lateinit var presenter: MainPresenter

    // View Fields
    private var loadingDialog: ProgressDialog? = null

    // Other Fields
    private var savedWordLists: List<WordList> = mutableListOf()


    /*
     * Lifecycle Functions
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /*
     * Listener Functions
     */

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_learn_words -> presenter.onClickLearnWords()
            R.id.fab_practice_words -> presenter.onClickPracticeWords()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_load_list -> presenter.onClickedLoadList()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
     * Helper Functions
     */

    override fun getLayoutId() = R.layout.activity_main

    override fun bindView() {

        // Setup Task FAB
        fab_learn_words.setOnClickListener(this)
        fab_practice_words.setOnClickListener(this)
    }

    override fun initializePresenter() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                MainActivityViewModel::class.java)
        presenter.setViewModel(viewModel)
        presenter.initialize()
    }

    private fun loadDefaultFragment() {
        addFragment(WordListFragment.newInstance(), R.id.main_container)
    }

    override fun showLoadingDialog(toShow: Boolean, title: String, content: String) {
        if (toShow) {
            buildLoadingDialog(title, content)?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    private fun buildLoadingDialog(title: String, content: String): ProgressDialog? {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
        loadingDialog?.let {
            it.setTitle(title)
            it.setMessage(content)
        }
        return loadingDialog
    }

    override fun dataInitialized() {
        loadDefaultFragment()
    }

    override fun updateSavedWordLists(savedWordLists: List<WordList>) {
        this.savedWordLists = savedWordLists
    }

    override fun showLoadSavedListDialog() {
        var wordListNames = mutableListOf<String>()
        savedWordLists.forEach { wordList -> wordListNames.add(wordList.name) }
        MaterialDialog.Builder(this)
                .title(R.string.title_load_saved_list)
                .items(wordListNames)
                .itemsCallback { _, _, which, _ ->
                    presenter.onClickedSavedListItem(savedWordLists[which])
                }.build().show()
    }

    override fun alertListTypeUpdate(wordListType: Int) {
        val selectedWordType = resources.getStringArray(R.array.items_list_types)[wordListType]
        showToast(resources.getString(R.string.alert_list_change, selectedWordType))
    }

    override fun startWordActivity(wordActivityMode: WordActivity.Companion.WordActivityMode) {
        val wordActivityIntent = Intent(this, WordActivity::class.java)
        wordActivityIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                wordActivityMode.ordinal)
        startActivity(wordActivityIntent)
        fab_new_task.collapse()
    }
}
