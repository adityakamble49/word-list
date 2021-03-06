package com.adityakamble49.wordlist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.utils.Constants

/**
 * Word List Model
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@Entity(tableName = Constants.Database.TABLE_WORD_LISTS,
        indices = [(Index(value = ["hash"], unique = true))])
data class WordList(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var hash: String,
        var marketplaceFilename: String,
        var name: String,
        var lastWordId: Int
)