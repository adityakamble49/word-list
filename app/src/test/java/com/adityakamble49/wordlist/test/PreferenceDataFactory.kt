package com.adityakamble49.wordlist.test

import com.adityakamble49.wordlist.model.DictateModeSpeed
import com.adityakamble49.wordlist.model.DictateModeType
import java.util.*

/**
 * Preference Data Factory
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
object PreferenceDataFactory {

    private val random = Random()

    fun makeDictateModeType(): DictateModeType {
        return DictateModeType.values()[random.nextInt(
                DictateModeType.values().size)]
    }

    fun makeDictateModeSpeed(): DictateModeSpeed {
        return DictateModeSpeed.values()[random.nextInt(
                DictateModeSpeed.values().size)]
    }
}