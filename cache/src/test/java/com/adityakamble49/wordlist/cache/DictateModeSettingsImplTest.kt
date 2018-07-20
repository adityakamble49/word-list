package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.cache.preference.PreferenceHelper
import com.adityakamble49.wordlist.cache.test.DictateModeSettingsFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * DictateModeSettingsImpl Test
 *
 * @author Aditya Kamble
 * @since 13/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class DictateModeSettingsImplTest {

    private val preferenceHelper = PreferenceHelper(
            RuntimeEnvironment.application.applicationContext)
    private val dictateModeSettingsCacheImpl = DictateModeSettingsCacheImpl(preferenceHelper)

    @Test
    fun getDictateModeSettingsCompletes() {
        val testObserver = dictateModeSettingsCacheImpl.getDictateModeSettings().test()
        testObserver.assertComplete()
    }

    @Test
    fun getDictateModeSettingsReturnsData() {
        val testDictateModeSettings = DictateModeSettingsFactory.makeDictateModeSettings()
        preferenceHelper.dictateModeSpeed = testDictateModeSettings.dictateModeSpeed.name.toLowerCase()
        preferenceHelper.dictateModeType = testDictateModeSettings.dictateModeType.name.toLowerCase()
        val testObserver = dictateModeSettingsCacheImpl.getDictateModeSettings().test()
        testObserver.assertValue(testDictateModeSettings)
    }
}