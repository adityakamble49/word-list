package com.adityakamble49.wordlist.ui.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.adityakamble49.wordlist.R

/**
 * Settings Fragment
 *
 * @author Aditya Kamble
 * @since 12/10/2018
 */
class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}
