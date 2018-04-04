package com.adityakamble49.wordlist.di.module

import android.app.Application
import android.content.Context
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import com.adityakamble49.wordlist.di.scope.PerActivity
import com.adityakamble49.wordlist.di.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Application Module
 *
 * @author Aditya Kamble
 * @since 5/4/2018
 */
@Module
class ApplicationModule {

    @Provides
    @PerApplication
    @ApplicationContext
    fun provideContext(application: Application): Context = application.applicationContext
}