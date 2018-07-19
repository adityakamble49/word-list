package com.adityakamble49.wordlist.cache

import com.adityakamble49.wordlist.cache.preference.PreferenceHelper
import com.adityakamble49.wordlist.data.repository.DictateModeSettingsCache
import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import com.adityakamble49.wordlist.domain.model.DictateModeSpeed.*
import com.adityakamble49.wordlist.domain.model.DictateModeType.*
import io.reactivex.Single
import javax.inject.Inject

/**
 * DictateModeSettings Cache Implementation
 *
 * @author Aditya Kamble
 * @since 13/7/2018
 */
class DictateModeSettingsCacheImpl @Inject constructor(
        private val preferenceHelper: PreferenceHelper) : DictateModeSettingsCache {

    override fun getDictateModeSettings(): Single<DictateModeSettings> {
        return Single.just(
                DictateModeSettings(getDictateModeType(), getDictateModeSpeed())
        )
    }

    private fun getDictateModeType() = when (preferenceHelper.dictateModeType) {
        WORD_COMPLETE_INFO.name.toLowerCase() -> WORD_COMPLETE_INFO
        WORD_DEFINITION.name.toLowerCase() -> WORD_DEFINITION
        WORD_ONLY.name.toLowerCase() -> WORD_ONLY
        else -> WORD_COMPLETE_INFO
    }

    private fun getDictateModeSpeed() = when (preferenceHelper.dictateModeSpeed) {
        SLOWER.name.toLowerCase() -> SLOWER
        SLOW.name.toLowerCase() -> SLOW
        NORMAL.name.toLowerCase() -> NORMAL
        FAST.name.toLowerCase() -> FAST
        FASTER.name.toLowerCase() -> FASTER
        else -> NORMAL
    }
}