package com.adityakamble49.wordlist.domain.exceptions

/**
 * Word List Name Exist Exception
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
class WordListNameExistException constructor(override val message: String) : Exception()