package com.adityakamble49.wordlist.ui.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import com.adityakamble49.wordlist.R

/**
 * Settings Fragment
 *
 * @author Aditya Kamble
 * @since 30/5/2018
 */
class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }
}
