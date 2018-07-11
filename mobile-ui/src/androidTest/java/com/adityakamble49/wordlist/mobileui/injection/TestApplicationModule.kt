package com.adityakamble49.wordlist.mobileui.injection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Test Application Module
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@Module
abstract class TestApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}
