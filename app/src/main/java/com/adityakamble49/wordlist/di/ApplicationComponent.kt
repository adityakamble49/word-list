package com.adityakamble49.wordlist.di

import android.app.Application
import com.adityakamble49.wordlist.WordListApp
import com.adityakamble49.wordlist.di.module.ActivityBindingModule
import com.adityakamble49.wordlist.di.module.ApplicationModule
import com.adityakamble49.wordlist.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */

@PerApplication
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ActivityBindingModule::class),
    (ApplicationModule::class)])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: WordListApp)
}