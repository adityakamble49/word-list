package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordListRepo
import javax.inject.Inject

/**
 * Get Word List Use Case
 *
 * @author Aditya Kamble
 * @since 15/4/2018
 */
class GetWordLists @Inject constructor(
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseExecutable() = wordListRepo.getWordLists()

    fun execute() = buildUseCaseExecutable()
}