package com.adityakamble49.wordlist.remote.model

/**
 * Marketplace Word List Model
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
data class MarketplaceWordList(
        var name: String,
        var type: String,
        var size: Int,
        var path: String,
        var sha: String,
        var url: String,
        var gitUrl: String,
        var htmlUrl: String,
        var downloadUrl: String,
        var _links: Links? = null
)