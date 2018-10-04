package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.related.RelatedWordsActivity
import javax.inject.Inject

/**
 * Main Activity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class MainActivity : BaseInjectableActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainActivityViewModel
    @Inject lateinit var database: WordListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                MainActivityViewModel::class.java)

        startActivity(Intent(this, RelatedWordsActivity::class.java))
    }
}
