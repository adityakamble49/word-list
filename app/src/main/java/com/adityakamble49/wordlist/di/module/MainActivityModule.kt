package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.ui.main.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityViewModelFactory(preferenceHelper: PreferenceHelper):
            MainActivityViewModelFactory = MainActivityViewModelFactory(
            preferenceHelper)

}