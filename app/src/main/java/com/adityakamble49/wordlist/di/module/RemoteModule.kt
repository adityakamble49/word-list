package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.remote.WordListService
import com.adityakamble49.wordlist.remote.WordListServiceFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient


/**
 * Remote Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
class RemoteModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
            WordListServiceFactory.makeOkHttpClient(BuildConfig.DEBUG)

    @Provides
    fun provideWordListService(okHttpClient: OkHttpClient): WordListService =
            WordListServiceFactory.makeWordListService(okHttpClient)
}