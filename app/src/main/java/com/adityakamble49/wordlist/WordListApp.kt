package com.adityakamble49.wordlist

import android.app.Application
import timber.log.Timber

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}