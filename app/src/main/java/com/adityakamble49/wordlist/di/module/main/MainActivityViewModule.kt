package com.adityakamble49.wordlist.di.module.main

import com.adityakamble49.wordlist.ui.main.MainActivity
import com.adityakamble49.wordlist.ui.main.MainContract
import dagger.Binds
import dagger.Module

/**
 * Main View Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
abstract class MainActivityViewModule {

    @Binds
    abstract fun provideMainActivityView(mainActivity: MainActivity): MainContract.View
}