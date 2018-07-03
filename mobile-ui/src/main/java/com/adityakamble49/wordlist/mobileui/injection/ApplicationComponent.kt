package com.adityakamble49.wordlist.mobileui.injection

import android.app.Application
import com.adityakamble49.wordlist.mobileui.WordListApp
import com.adityakamble49.wordlist.mobileui.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Application Component
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ApplicationModule::class),
    (UIModule::class),
    (PresentationModule::class),
    (DataModule::class),
    (CacheModule::class)])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: WordListApp)
}