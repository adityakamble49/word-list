package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.di.module.main.MainActivityModule
import com.adityakamble49.wordlist.di.module.main.MainActivityViewModule
import com.adityakamble49.wordlist.di.module.main.MainFragmentsModule
import com.adityakamble49.wordlist.di.scope.PerActivity
import com.adityakamble49.wordlist.ui.main.MainActivity
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
    @ContributesAndroidInjector(modules = [(MainActivityViewModule::class),
        (MainActivityModule::class), (MainFragmentsModule::class)])
    abstract fun contributeMainActivity(): MainActivity
}