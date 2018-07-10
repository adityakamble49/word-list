package com.adityakamble49.wordlist.mobileui.injection.module

import android.app.Application
import com.adityakamble49.wordlist.cache.WordListCacheImpl
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.data.repository.WordListCache
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Cache Module
 *
 * @author Aditya Kamble
 * @since 3/7/2018
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

    @Binds
    abstract fun bindWordListCache(wordListCache: WordListCacheImpl): WordListCache
}