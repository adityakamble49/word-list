package com.adityakamble49.wordlist.data

import com.adityakamble49.wordlist.data.repository.DictateModeSettingsCache
import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import com.adityakamble49.wordlist.domain.repository.DictateModeSettingsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * DictateModeSettings Data Repository
 *
 * @author Aditya Kamble
 * @since 19/7/2018
 */
class DictateModeSettingsDataRepository @Inject constructor(
        private val dictateModeSettingsCache: DictateModeSettingsCache) :
        DictateModeSettingsRepository {

    override fun getDictateModeSettings(): Single<DictateModeSettings> {
        return dictateModeSettingsCache.getDictateModeSettings()
    }
}