package com.adityakamble49.wordlist.data.model

/**
 * Marketplace Word List Entity
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
data class MarketplaceWordListEntity(
        var name: String,
        var sha: String,
        var downloadUrl: String,
        var status: MarketplaceWordListStatus
)