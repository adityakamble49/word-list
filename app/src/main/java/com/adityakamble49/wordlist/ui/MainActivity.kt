package com.adityakamble49.wordlist.ui

import android.os.Bundle
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.list.WordListFragment
import com.adityakamble49.wordlist.utils.addFragment

class MainActivity : BaseInjectableActivity() {


    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /*
     * Helper Functions
     */

    override fun bindView() {
        loadDefaultFragment()
    }

    private fun loadDefaultFragment() {
        addFragment(WordListFragment.newInstance(), R.id.main_container)
    }
}
