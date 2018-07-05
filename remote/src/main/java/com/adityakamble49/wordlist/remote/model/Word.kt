package com.adityakamble49.wordlist.remote.model

/**
 * Word Model
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
data class Word(
        var name: String,
        var type: String,
        var pronunciation: String,
        var information: String,
        var mnemonic: String
)