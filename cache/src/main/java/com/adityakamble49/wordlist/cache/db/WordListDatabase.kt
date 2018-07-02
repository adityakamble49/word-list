package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.Database
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

    abstract fun cachedWordListDao(): CachedWordListDao
}