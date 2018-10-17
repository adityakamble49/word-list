package com.adityakamble49.wordlist.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.utils.addFragment

/**
 * Settings Activity
 *
 * @author Aditya Kamble
 * @since 12/10/2018
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        bindView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        addFragment(SettingsFragment.newInstance(), R.id.fl_settings)
    }
}