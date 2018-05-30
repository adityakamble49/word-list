package com.adityakamble49.wordlist.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Settings Activity
 *
 * @author Aditya Kamble
 * @since 30/5/2018
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup SettingsFragment
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }
}