package com.adityakamble49.wordlist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.adityakamble49.wordlist.utils.Constants.Database

/**
 * Word Model
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Entity(tableName = Database.TABLE_WORD_NAME)
data class Word(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var listType: Int,
        var name: String,
        var type: String,
        var pronunciation: String,
        var information: String,
        var mnemonic: String
)