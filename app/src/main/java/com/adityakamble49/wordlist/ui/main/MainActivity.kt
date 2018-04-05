package com.adityakamble49.wordlist.ui.main

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

    // View Fields

    // Other Fields
    private lateinit var presenter: MainPresenter


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
        presenter = MainPresenter(this, viewModel)
    }

    private fun loadDefaultFragment() {
        addFragment(WordListFragment.newInstance(), R.id.main_container)
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
