package com.adityakamble49.wordlist.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.utils.Constants.Database

/**
 * Marketplace Models
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
@Entity(tableName = Database.TABLE_MARKETPLACE_WORDLIST)
data class MarketplaceWordList(
        @PrimaryKey var name: String,
        var type: String,
        var size: Int,
        var path: String,
        var sha: String,
        var url: String,
        var gitUrl: String,
        var htmlUrl: String,
        var downloadUrl: String,
        @Embedded var _links: Links? = null
)

data class Links(
        var self: String,
        var git: String,
        var html: String
)