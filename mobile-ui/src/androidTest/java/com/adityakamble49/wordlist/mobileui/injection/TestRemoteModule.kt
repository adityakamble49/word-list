package com.adityakamble49.wordlist.mobileui.injection

import com.adityakamble49.wordlist.remote.service.WordListService
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * Test Remote Module
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@Module
class TestRemoteModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return mock()
    }

    @Provides
    fun provideWordListService(): WordListService {
        return mock()
    }
}
