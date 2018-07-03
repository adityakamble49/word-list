package com.adityakamble49.wordlist.presentation.model

/**
 * Word List View
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
data class WordListView(
        var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String,
        var lastWordId: Int
)