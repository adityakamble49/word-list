package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.MenuItem
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import kotlinx.android.synthetic.main.activity_word.*
import javax.inject.Inject

/**
 * Word Activity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class WordActivity : BaseInjectableActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        bindView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindView() {
        showTitleOnCollapseOnly()
    }

    private fun showTitleOnCollapseOnly() {
        collapsing_toolbar.title = resources.getString(R.string.word_name_default)
        var isShow = true
        var scrollRange = -1
        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsing_toolbar.title = resources.getString(R.string.word_name_default)
                isShow = true
            } else if (isShow) {
                collapsing_toolbar.title = ""
                isShow = false
            }
        }
    }
}
