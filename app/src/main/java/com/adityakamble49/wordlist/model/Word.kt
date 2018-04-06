package com.adityakamble49.wordlist.model

/**
 * Word Model
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
data class Word(
        val name: String,
        val type: String,
        val pronunciation: String,
        val information: String,
        val mnemonic: String
)