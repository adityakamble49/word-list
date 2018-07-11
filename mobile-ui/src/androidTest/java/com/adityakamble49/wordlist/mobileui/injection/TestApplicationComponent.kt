package com.adityakamble49.wordlist.mobileui.injection

import android.app.Application
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import com.adityakamble49.wordlist.mobileui.injection.module.PresentationModule
import com.adityakamble49.wordlist.mobileui.injection.module.UIModule
import com.adityakamble49.wordlist.mobileui.test.TestWordListApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Test Application Component
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (TestApplicationModule::class),
    (UIModule::class),
    (PresentationModule::class),
    (TestDataModule::class),
    (TestCacheModule::class),
    (TestRemoteModule::class)])
interface TestApplicationComponent {

    fun wordListRepository(): WordListRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

    fun inject(app: TestWordListApp)
}