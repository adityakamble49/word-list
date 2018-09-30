package com.adityakamble49.wordlist.di

import android.app.Application
import com.adityakamble49.wordlist.WordListApp
import com.adityakamble49.wordlist.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Application Component
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ApplicationModule::class),
    (CacheModule::class),
    (RemoteModule::class),
    (PresentationModule::class),
    (UIModule::class)])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: WordListApp)
}