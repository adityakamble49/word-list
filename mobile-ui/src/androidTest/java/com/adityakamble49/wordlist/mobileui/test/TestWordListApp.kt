package com.adityakamble49.wordlist.mobileui.test

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import com.adityakamble49.wordlist.mobileui.injection.DaggerTestApplicationComponent
import com.adityakamble49.wordlist.mobileui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Test Application
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
class TestWordListApp : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestApplicationComponent

    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext()
                    .applicationContext as TestWordListApp).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}