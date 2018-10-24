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
        var etymology: String,
        var pronunciation: String,
        var mnemonic: String,
        var information: ArrayList<WordInformation>
)

/**
 * Word Information
 *
 * @author Aditya Kamble
 * @since 3/10/2018
 */
data class WordInformation(
        var type: String,
        var definition: String,
        var example: String
)