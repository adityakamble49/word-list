package com.adityakamble49.wordlist.di.module

import android.app.Application
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import dagger.Module
import dagger.Provides


/**
 * Cache Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideDatabase(application: Application): WordListDatabase {
            return WordListDatabase.getInstance(application.applicationContext)
        }
    }
}