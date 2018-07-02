package com.adityakamble49.wordlist.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.Constants

/**
 * Word List Cache Entity
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
@Entity(tableName = Constants.TABLE_WORD_LISTS,
        indices = [(Index(value = ["hash"], unique = true)),
            (Index(value = ["name"], unique = true))])
data class CachedWordList(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String,
        var lastWordId: Int
)