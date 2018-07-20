package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.domain.model.DictateModeSettings

/**
 * Dictate Mode Settings Factory
 * It provides sample data for testing
 *
 * @author Aditya Kamble
 * @since 20/7/2018
 */
object DictateModeSettingsFactory {

    fun makeDictateModeSettings(): DictateModeSettings {
        return DictateModeSettings(PreferenceDataFactory.makeDictateModeType(),
                PreferenceDataFactory.makeDictateModeSpeed())
    }
}