package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.model.DictateModeSpeed
import com.adityakamble49.wordlist.model.DictateModeType
import com.adityakamble49.wordlist.test.PreferenceDataFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Preference Helper Test
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
@RunWith(RobolectricTestRunner::class)
class PreferenceHelperTest {

    private val preferenceHelper = PreferenceHelper(
            RuntimeEnvironment.application.applicationContext)

    @Test
    fun dictateModeTypeDefaultTest() {
        // Default Dictate Mode Type
        val defaultDictateModeType = DictateModeType.WORD_COMPLETE_INFO.name.toLowerCase()

        // Get Dictate Mode Type and Assert
        val dictateModeTypeReturn = preferenceHelper.dictateModeType
        assertEquals(defaultDictateModeType, dictateModeTypeReturn)
    }

    @Test
    fun dictateModeTypeChangedTest() {
        // Set Dictate Mode Type
        val dictateModeType = PreferenceDataFactory.makeDictateModeType().name.toLowerCase()
        preferenceHelper.dictateModeType = dictateModeType

        // Get Dictate Mode Type and Assert
        val dictateModeTypeReturn = preferenceHelper.dictateModeType
        assertEquals(dictateModeType, dictateModeTypeReturn)
    }

    @Test
    fun dictateModeSpeedDefaultTest() {
        // Default Dictate Mode Speed
        val defaultDictateModeSpeed = DictateModeSpeed.NORMAL.name.toLowerCase()

        // Get Dictate Mode Speed and Assert
        val dictateModeSpeedReturn = preferenceHelper.dictateModeSpeed
        assertEquals(defaultDictateModeSpeed, dictateModeSpeedReturn)
    }

    @Test
    fun dictateModeSpeedChangedTest() {
        // Set Dictate Mode Speed
        val dictateModeSpeed = PreferenceDataFactory.makeDictateModeSpeed().name.toLowerCase()
        preferenceHelper.dictateModeSpeed = dictateModeSpeed

        // Get Dictate Mode Speed and Assert
        val dictateModeSpeedReturn = preferenceHelper.dictateModeSpeed
        assertEquals(dictateModeSpeed, dictateModeSpeedReturn)
    }
}