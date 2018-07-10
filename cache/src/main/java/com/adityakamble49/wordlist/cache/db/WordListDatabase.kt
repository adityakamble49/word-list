package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.adityakamble49.wordlist.cache.dao.CachedWordListDao
import com.adityakamble49.wordlist.cache.model.CachedWordList

/**
 * Word List Database
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
@Database(entities = [(CachedWordList::class)], version = 1)
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
                                WordListDatabase::class.java, Constants.DB_NAME).build()
                    }
                    return instance as WordListDatabase
                }
            }
            return instance as WordListDatabase
        }
    }

    abstract fun cachedWordListDao(): CachedWordListDao
}