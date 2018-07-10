package com.adityakamble49.wordlist.mobileui.injection.module

import com.adityakamble49.wordlist.mobileui.BuildConfig
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.service.WordListServiceFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient


/**
 * Remote Module
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */
@Module
class RemoteModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = WordListServiceFactory.makeOkHttpClient(
            BuildConfig.DEBUG)

    @Provides
    fun provideWordListService(
            okHttpClient: OkHttpClient): WordListService = WordListServiceFactory.makeWordListService(
            okHttpClient)
}