package com.adityakamble49.wordlist.domain.model

/**
 * Word List Model
 *
 * @author Aditya Kamble
 * @since 1/1/2018
 */
data class WordList(
        var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String,
        var lastWordId: Int
)