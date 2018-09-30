package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * UI Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
abstract class UIModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}