package com.adityakamble49.wordlist.cache.db

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
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
        fun getInstance(application: Application): WordListDatabase {
            return Room.databaseBuilder(application.applicationContext,
                    WordListDatabase::class.java, Constants.DB_NAME).build()
        }
    }

    abstract fun cachedWordListDao(): CachedWordListDao
}