package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import javax.inject.Inject

/**
 * Check Word Imported Use Case
 *
 * @author Aditya Kamble
 * @since 15/4/2018
 */
class AreWordsImported @Inject constructor(
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseExecutable() = preferenceHelper.areWordsImported

    fun execute() = buildUseCaseExecutable()
}