package com.adityakamble49.wordlist.data.model

/**
 * Word Entity
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
data class WordEntity(
        var id: Int,
        var listId: Int,
        var name: String,
        var type: String,
        var pronunciation: String,
        var information: String,
        var mnemonic: String
)