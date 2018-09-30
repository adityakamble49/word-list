package com.adityakamble49.wordlist.cache.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.cache.db.DBConstants

/**
 * Word Entity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Entity(tableName = DBConstants.TABLE_WORD)
data class Word(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var information: String,
        var mnemonics: String
)