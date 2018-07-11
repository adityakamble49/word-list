package com.adityakamble49.wordlist.mobileui.injection

import android.app.Application
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.data.repository.WordListCache
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides

/**
 * Test Cache Module
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@Module
class TestCacheModule {

    @Provides
    fun provideDatabase(application: Application): WordListDatabase {
        return WordListDatabase.getInstance(application.applicationContext)
    }

    @Provides
    fun provideWordListCache(): WordListCache {
        return mock()
    }
}
