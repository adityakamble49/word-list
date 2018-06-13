package com.adityakamble49.wordlist.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.cache.db.WordListDatabase
import com.adityakamble49.wordlist.di.module.common.ViewModelModule
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.remote.WordListService
import com.adityakamble49.wordlist.remote.WordListServiceFactory
import com.adityakamble49.wordlist.utils.Constants.Database
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * Application Module
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
@Module(includes = [(ViewModelModule::class)])
class ApplicationModule {

    @Provides
    @PerApplication
    @ApplicationContext
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @PerApplication
    fun provideWordListDatabase(application: Application): WordListDatabase =
            Room.databaseBuilder(application.applicationContext, WordListDatabase::class.java,
                    Database.DB_NAME).build()

    @Provides
    @PerApplication
    fun provideWordDao(wordListDatabase: WordListDatabase) = wordListDatabase.wordDao()

    @Provides
    @PerApplication
    fun provideWordListDao(wordListDatabase: WordListDatabase) = wordListDatabase.wordListDao()

    @Provides
    @PerApplication
    fun provideOkHttpClient(): OkHttpClient = WordListServiceFactory.makeOkHttpClient(
            BuildConfig.DEBUG)

    @Provides
    @PerApplication
    fun provideWordListService(okHttpClient: OkHttpClient): WordListService =
            WordListServiceFactory.makeWordListService(okHttpClient)
}