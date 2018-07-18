package com.adityakamble49.wordlist.cache.preference

import android.content.Context
import android.content.SharedPreferences
import com.adityakamble49.wordlist.cache.model.CachedDictateModeSpeed
import com.adityakamble49.wordlist.cache.model.CachedDictateModeType
import javax.inject.Inject

/**
 * Preference Helper
 *
 * @author Aditya Kamble
 * @since 13/7/2018
 */
class PreferenceHelper @Inject constructor(private val context: Context) {

    companion object {
        const val PREF_WORDLIST_PACKAGE_NAME = "com.adityakamble49.wordlist"
        const val PREF_WORDLIST_SETTINGS = "com.adityakamble49.wordlist_preferences"
        const val PREF_KEY_DICTATE_MODE_TYPE = "dictate_mode_type"
        val DEFAULT_DICTATE_MODE_TYPE = CachedDictateModeType.WORD_COMPLETE_INFO.name.toLowerCase()
        const val PREF_KEY_DICTATE_MODE_SPEED = "dictate_mode_speed"
        val DEFAULT_DICTATE_MODE_SPEED = CachedDictateModeSpeed.NORMAL.name.toLowerCase()
    }

    private val wordListPref: SharedPreferences
    private val wordListSettings: SharedPreferences

    init {
        wordListPref = context.getSharedPreferences(PREF_WORDLIST_PACKAGE_NAME,
                Context.MODE_PRIVATE)
        wordListSettings = context.getSharedPreferences(PREF_WORDLIST_SETTINGS,
                Context.MODE_PRIVATE)
    }

    var dictateModeType: String
        get() = wordListSettings.getString(PREF_KEY_DICTATE_MODE_TYPE, DEFAULT_DICTATE_MODE_TYPE)
        set(dictateModeType) = wordListSettings.edit().putString(PREF_KEY_DICTATE_MODE_TYPE,
                dictateModeType).apply()

    var dictateModeSpeed: String
        get() = wordListSettings.getString(PREF_KEY_DICTATE_MODE_SPEED, DEFAULT_DICTATE_MODE_SPEED)
        set(dictateModeSpeed) = wordListSettings.edit().putString(PREF_KEY_DICTATE_MODE_SPEED,
                dictateModeSpeed).apply()
}
