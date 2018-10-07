package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.MenuItem
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }
}
