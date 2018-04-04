package com.adityakamble49.wordlist.model

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
data class Word(
        val name: String,
        val strippedName: String,
        val information: String,
        val mnemonic: String
)