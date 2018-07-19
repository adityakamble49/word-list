package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import io.reactivex.Single

/**
 * DictateModeSettings Cache Interface
 *
 * @author Aditya Kamble
 * @since 19/7/2018
 */
interface DictateModeSettingsCache {

    fun getDictateModeSettings(): Single<DictateModeSettings>
}