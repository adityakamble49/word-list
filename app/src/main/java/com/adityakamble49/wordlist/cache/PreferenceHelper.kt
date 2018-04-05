package com.adityakamble49.wordlist.cache

import android.content.Context
import android.content.SharedPreferences
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import javax.inject.Inject

/**
 * Preference Helper
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
class PreferenceHelper @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val PREF_WORDLIST_PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private const val PREF_KEY_CURRENT_LIST_TYPE = "current_list_type"
        const val DEFAULT_CURRENT_LIST_TYPE = 0
    }

    private val wordListPref: SharedPreferences

    init {
        wordListPref = context.getSharedPreferences(PREF_WORDLIST_PACKAGE_NAME,
                Context.MODE_PRIVATE)
    }

    var currentListType: Int
        get() = wordListPref.getInt(PREF_KEY_CURRENT_LIST_TYPE, DEFAULT_CURRENT_LIST_TYPE)
        set(currentListType) = wordListPref.edit().putInt(PREF_KEY_CURRENT_LIST_TYPE,
                currentListType).apply()
}
