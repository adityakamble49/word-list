package com.adityakamble49.wordlist.cache

import android.content.Context
import android.content.SharedPreferences
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import com.adityakamble49.wordlist.model.DictateModeSpeed
import com.adityakamble49.wordlist.model.DictateModeType
import javax.inject.Inject

/**
 * Preference Helper
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class PreferenceHelper @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val PREF_WORDLIST_PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private const val PREF_WORDLIST_SETTINGS = "${BuildConfig.APPLICATION_ID}_preferences"
        private const val PREF_KEY_DICTATE_MODE_TYPE = "dictate_mode_type"
        val DEFAULT_DICTATE_MODE_TYPE = DictateModeType.WORD_COMPLETE_INFO.name.toLowerCase()
        private const val PREF_KEY_DICTATE_MODE_SPEED = "dictate_mode_speed"
        val DEFAULT_DICTATE_MODE_SPEED = DictateModeSpeed.NORMAL.name.toLowerCase()
    }

    private val wordListPref: SharedPreferences
    private val wordListSettings: SharedPreferences

    var dictateModeType: String
        set(dictateModeType) = wordListSettings.edit().putString(PREF_KEY_DICTATE_MODE_TYPE,
                dictateModeType).apply()
        get() = wordListSettings.getString(PREF_KEY_DICTATE_MODE_TYPE, DEFAULT_DICTATE_MODE_TYPE)

    var dictateModeSpeed: String
        set(dictateModeSpeed) = wordListSettings.edit().putString(PREF_KEY_DICTATE_MODE_SPEED,
                dictateModeSpeed).apply()
        get() = wordListSettings.getString(PREF_KEY_DICTATE_MODE_SPEED, DEFAULT_DICTATE_MODE_SPEED)

    init {
        wordListPref = context.getSharedPreferences(PREF_WORDLIST_PACKAGE_NAME,
                Context.MODE_PRIVATE)
        wordListSettings = context.getSharedPreferences(PREF_WORDLIST_SETTINGS,
                Context.MODE_PRIVATE)

        dictateModeType = DEFAULT_DICTATE_MODE_TYPE
        dictateModeSpeed = DEFAULT_DICTATE_MODE_SPEED
    }
}
