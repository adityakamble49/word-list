package com.adityakamble49.wordlist.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.db.Constants

/**
 * Word Model
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Entity(tableName = Constants.TABLE_WORD)
data class Word(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var information: String,
        var mnemonics: String
)