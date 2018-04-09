package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList

/**
 * Word List Database
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Database(entities = [(Word::class), (WordList::class)], version = 1)
@TypeConverters(Converter::class)
abstract class WordListDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun wordListDao(): WordListDao

}