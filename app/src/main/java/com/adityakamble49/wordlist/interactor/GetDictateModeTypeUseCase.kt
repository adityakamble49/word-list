package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.utils.Constants
import com.adityakamble49.wordlist.utils.Constants.DictateModeTypes.*
import javax.inject.Inject

/**
 * Get Dictate Mode Type UseCase
 *
 * @author Aditya Kamble
 * @since 30/5/2018
 */
class GetDictateModeTypeUseCase @Inject constructor(
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseExecutable(): Constants.DictateModeTypes {
        return when (preferenceHelper.dictateModeType) {
            WORD_COMPLETE_INFO.name.toLowerCase() -> WORD_COMPLETE_INFO
            WORD_DEFINITION.name.toLowerCase() -> WORD_DEFINITION
            WORD_ONLY.name.toLowerCase() -> WORD_ONLY
            else -> WORD_COMPLETE_INFO
        }
    }

    fun execute() = buildUseCaseExecutable()
}