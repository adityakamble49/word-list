package com.adityakamble49.wordlist.data

import com.adityakamble49.wordlist.data.repository.DictateModeSettingsCache
import com.adityakamble49.wordlist.data.test.DictateModeSettingsFactory
import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * DictateModeSettings Data Repository Test
 *
 * @author Aditya Kamble
 * @since 20/7/2018
 */
@RunWith(JUnit4::class)
class DictateModeSettingsDataRepositoryTest {

    @Mock private lateinit var cache: DictateModeSettingsCache
    private lateinit var dictateModeSettingsDataRepository: DictateModeSettingsDataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dictateModeSettingsDataRepository = DictateModeSettingsDataRepository(
                cache)
    }

    @Test
    fun getDictateModeSettingsComplete() {
        stubDictateModeCacheGetDictateModeSettings(
                Single.just(DictateModeSettingsFactory.makeDictateModeSettings()))
        val testObserver = dictateModeSettingsDataRepository.getDictateModeSettings().test()
        testObserver.assertComplete()
    }

    @Test
    fun getDictateModeSettingsReturns() {
        val testDictateModeSettings = DictateModeSettingsFactory.makeDictateModeSettings()
        stubDictateModeCacheGetDictateModeSettings(Single.just(testDictateModeSettings))
        val testObserver = dictateModeSettingsDataRepository.getDictateModeSettings().test()
        testObserver.assertValue(testDictateModeSettings)
    }

    private fun stubDictateModeCacheGetDictateModeSettings(single: Single<DictateModeSettings>) {
        whenever(cache.getDictateModeSettings()).thenReturn(single)
    }
}