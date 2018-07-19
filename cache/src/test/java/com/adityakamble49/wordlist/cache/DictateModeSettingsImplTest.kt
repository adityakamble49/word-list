package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.cache.preference.PreferenceHelper
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

/**
 * DictateModeSettingsImpl Test
 *
 * @author Aditya Kamble
 * @since 13/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class DictateModeSettingsImplTest {

    private lateinit var dictateModeSettingsCacheImpl: DictateModeSettingsCacheImpl
    @Inject lateinit var preferenceHelper: PreferenceHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dictateModeSettingsCacheImpl = DictateModeSettingsCacheImpl(preferenceHelper)
    }
}