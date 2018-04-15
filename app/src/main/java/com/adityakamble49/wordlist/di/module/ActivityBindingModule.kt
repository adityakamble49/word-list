package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.di.module.main.MainActivityModule
import com.adityakamble49.wordlist.di.module.main.MainFragmentsModule
import com.adityakamble49.wordlist.di.module.word.WordActivityModule
import com.adityakamble49.wordlist.di.scope.PerActivity
import com.adityakamble49.wordlist.ui.main.MainActivity
import com.adityakamble49.wordlist.ui.word.WordActivity
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
            modules = [(MainActivityModule::class), (MainFragmentsModule::class)])
    abstract fun contributeMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(WordActivityModule::class)])
    abstract fun contributeWordViewPagerActivity(): WordActivity
}