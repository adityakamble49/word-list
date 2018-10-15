package com.adityakamble49.wordlist.cache.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.DBConstants

/**
 * Word Entity
 *
 * @author Aditya Kamble
 * @since 15/10/2018
 */
@Entity(tableName = DBConstants.TABLE_MARKETPLACE_WORDLIST)
data class MarketplaceWordList(
        @PrimaryKey var name: String,
        var type: String,
        var size: Int,
        var path: String,
        var sha: String,
        var status: String = WordListStatus.NOT_AVAILABLE.name,
        var url: String,
        var downloadUrl: String
)

enum class WordListStatus {
    NOT_AVAILABLE, DOWNLOADING, AVAILABLE, UPDATE_AVAILABLE
}