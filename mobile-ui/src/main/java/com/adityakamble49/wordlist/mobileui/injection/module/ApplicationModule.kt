package com.adityakamble49.wordlist.mobileui.injection.module

import android.app.Application
import android.content.Context
import com.adityakamble49.wordlist.mobileui.injection.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application Module
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(application: Application): Context = application.applicationContext
}