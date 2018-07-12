package com.adityakamble49.wordlist.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.Constants

/**
 * Cached Word
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@Entity(tableName = Constants.TABLE_WORDS)
data class CachedWord(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var listId: Int,
        var name: String,
        var type: String,
        var pronunciation: String,
        var information: String,
        var mnemonic: String
)