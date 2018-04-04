package com.adityakamble49.wordlist.ui.common

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Base Injectable Activity
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
abstract class BaseInjectableActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
}