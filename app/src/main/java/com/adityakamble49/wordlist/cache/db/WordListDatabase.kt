package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.adityakamble49.wordlist.cache.dao.WordDao
import com.adityakamble49.wordlist.cache.dao.WordListDao
import com.adityakamble49.wordlist.cache.dao.WordListWordJoinDao
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.cache.entities.WordList
import com.adityakamble49.wordlist.cache.entities.WordListWordJoin

/**
 * Word List Database
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Database(
        entities = [
            (Word::class),
            (WordList::class),
            (WordListWordJoin::class)
        ],
        version = 1)
@TypeConverters(Converter::class)
abstract class WordListDatabase : RoomDatabase() {

    companion object {
        private var instance: WordListDatabase? = null
        private val lock = Any()
        fun getInstance(context: Context): WordListDatabase {
            if (instance == null) {
                synchronized(lock) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context,
                                WordListDatabase::class.java, DBConstants.DB_NAME).build()
                    }
                    return instance as WordListDatabase
                }
            }
            return instance as WordListDatabase
        }
    }


    abstract fun wordDao(): WordDao

    abstract fun wordListDao(): WordListDao

    abstract fun wordListWordJoinDao(): WordListWordJoinDao
}