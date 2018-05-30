package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.model.DictateModeConfig
import com.adityakamble49.wordlist.model.DictateModeSpeed.*
import com.adityakamble49.wordlist.model.DictateModeType.*
import javax.inject.Inject

/**
 * Get Dictate Mode Config UseCase
 *
 * @author Aditya Kamble
 * @since 30/5/2018
 */
class GetDictateModeConfigUseCase @Inject constructor(
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseExecutable() = DictateModeConfig(getDictateModeType(),
            getDictateModeSpeed())

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

    fun execute() = buildUseCaseExecutable()
}