package com.adityakamble49.wordlist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.adityakamble49.wordlist.utils.Constants

/**
 * Word List Join
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
@Entity(tableName = Constants.Database.TABLE_WORD_LIST_JOIN,
        primaryKeys = ["wordId", "wordListId"],
        foreignKeys = [
            ForeignKey(entity = Word::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("wordId")),
            ForeignKey(entity = WordList::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("wordListId"))
        ])
data class WordListJoin(
        val wordId: Int,
        val wordListId: Int,
        val wordPosition: Int
)