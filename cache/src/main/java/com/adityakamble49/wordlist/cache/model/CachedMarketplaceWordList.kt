package com.adityakamble49.wordlist.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.Constants

/**
 * Cached Marketplace Word List
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@Entity(tableName = Constants.TABLE_MARKETPLACE_WORDLIST)
data class CachedMarketplaceWordList(
        @PrimaryKey var name: String,
        var sha: String,
        var downloadUrl: String,
        var status: String = CachedMarketplaceWordListStatus.NOT_AVAILABLE.name
)