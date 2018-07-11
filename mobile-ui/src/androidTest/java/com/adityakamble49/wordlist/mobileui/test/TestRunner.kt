package com.adityakamble49.wordlist.mobileui.test

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

/**
 * Test Runner
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
class TestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Throws(InstantiationException::class, IllegalAccessException::class,
            ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestWordListApp::class.java.name, context)
    }
}