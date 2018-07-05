package com.adityakamble49.wordlist.data.model

/**
 * Word List Entity Model
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
data class WordListEntity(
        var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String,
        var lastWordId: Int
)