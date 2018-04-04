package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.di.scope.PerActivity
import com.adityakamble49.wordlist.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *  Activity Binding Module used to enable Android Injector in activity
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(
            modules = [(MainFragmentsModule::class)])
    abstract fun contributeMainActivity(): MainActivity
}