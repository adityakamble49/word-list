package com.adityakamble49.wordlist.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}