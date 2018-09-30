package com.adityakamble49.wordlist.cache.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.DBConstants

/**
 * Word List Entity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Entity(tableName = DBConstants.TABLE_WORD_LISTS,
        indices = [(Index(value = ["hash"], unique = true))])
data class WordList(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String
)