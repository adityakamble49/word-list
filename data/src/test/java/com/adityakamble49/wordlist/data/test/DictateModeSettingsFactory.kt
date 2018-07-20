package com.adityakamble49.wordlist.data.test

import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import com.adityakamble49.wordlist.domain.model.DictateModeSpeed
import com.adityakamble49.wordlist.domain.model.DictateModeType
import java.util.*

/**
 * Dictate Mode Settings Factory
 * It provides sample data for testing
 *
 * @author Aditya Kamble
 * @since 20/7/2018
 */
object DictateModeSettingsFactory {

    private val random = Random()

    fun makeDictateModeType(): DictateModeType {
        return DictateModeType.values()[random.nextInt(
                DictateModeType.values().size)]
    }

    fun makeDictateModeSpeed(): DictateModeSpeed {
        return DictateModeSpeed.values()[random.nextInt(
                DictateModeSpeed.values().size)]
    }

    fun makeDictateModeSettings(): DictateModeSettings {
        return DictateModeSettings(makeDictateModeType(), makeDictateModeSpeed())
    }
}