package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListJoin

/**
 * Word List Database
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Database(entities = [(Word::class), (WordList::class), (WordListJoin::class)], version = 1)
abstract class WordListDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun wordListDao(): WordListDao
    abstract fun wordListJoinDao(): WordListJoinDao

}