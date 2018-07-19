package com.adityakamble49.wordlist.domain.repository

import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import io.reactivex.Single

/**
 * DictateModeSettings Repository
 * This interface will be implemented by data layer
 * Contract to access DictateModeSettings related data
 *
 * @author Aditya Kamble
 * @since 19/7/2018
 */
interface DictateModeSettingsRepository {

    fun getDictateModeSettings(): Single<DictateModeSettings>
}