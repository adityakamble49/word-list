package com.adityakamble49.wordlist.interactor

/**
 * Word List Name Exist Exception
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class WordListNameExistException constructor(override val message: String) : Exception()