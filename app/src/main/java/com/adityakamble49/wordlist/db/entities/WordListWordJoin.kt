package com.adityakamble49.wordlist.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.adityakamble49.wordlist.db.Constants

@Entity(tableName = Constants.TABLE_WORD_LISTS_WORDS_JOIN,
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