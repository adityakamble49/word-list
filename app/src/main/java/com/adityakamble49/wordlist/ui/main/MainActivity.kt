package com.adityakamble49.wordlist.ui.main

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.list.WordListFragment
import com.adityakamble49.wordlist.utils.addFragment
import com.adityakamble49.wordlist.utils.showToast
import com.afollestad.materialdialogs.MaterialDialog
import javax.inject.Inject

class MainActivity : BaseInjectableActivity(), MainContract.View {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: MainActivityViewModelFactory
    @Inject lateinit var presenter: MainPresenter

    // View Fields
    private var loadingDialog: ProgressDialog? = null

    // Other Fields


    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_change_list_type -> presenter.onClickedChangeListType()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
     * Helper Functions
     */

    override fun bindView() {
        loadDefaultFragment()
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

    override fun showChangeListTypeDialog(selectedWordListType: Int) {
        MaterialDialog.Builder(this)
                .title(R.string.title_change_list_type_dialog)
                .items(R.array.items_list_types)
                .itemsCallbackSingleChoice(selectedWordListType) { _, _, which, _ ->
                    presenter.onListTypeSelected(which)
                    true
                }.build().show()
    }

    override fun alertListTypeUpdate(wordListType: Int) {
        val selectedWordType = resources.getStringArray(R.array.items_list_types)[wordListType]
        showToast(resources.getString(R.string.alert_list_change, selectedWordType))
    }
}
