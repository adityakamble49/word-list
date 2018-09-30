package com.adityakamble49.wordlist.cache.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import com.adityakamble49.wordlist.cache.db.DBConstants

/**
 * Word List Word Join
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Entity(tableName = DBConstants.TABLE_WORD_LISTS_WORDS_JOIN,
        indices = [Index(value = ["wordId"])],
        primaryKeys = ["wordListId", "wordId"],
        foreignKeys = [
            (ForeignKey(entity = WordList::class,
                    parentColumns = ["id"],
                    childColumns = ["wordListId"])),
            (ForeignKey(entity = Word::class,
                    parentColumns = ["id"],
                    childColumns = ["wordId"]))
        ]
)
class WordListWordJoin(
        var wordListId: Int,
        var wordId: Int
)