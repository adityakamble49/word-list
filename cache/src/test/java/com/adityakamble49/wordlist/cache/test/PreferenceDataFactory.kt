package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.cache.model.CachedDictateModeSpeed
import com.adityakamble49.wordlist.cache.model.CachedDictateModeType
import java.util.*

/**
 * Preference Data Factory
 *
 * @author Aditya Kamble
 * @since 13/7/2018
 */
object PreferenceDataFactory {

    private val random = Random()

    fun makeDictateModeType(): CachedDictateModeType {
        return CachedDictateModeType.values()[random.nextInt(
                CachedDictateModeType.values().size)]
    }

    fun makeDictateModeSpeed(): CachedDictateModeSpeed {
        return CachedDictateModeSpeed.values()[random.nextInt(
                CachedDictateModeSpeed.values().size)]
    }
}