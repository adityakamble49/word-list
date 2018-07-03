package com.adityakamble49.wordlist.mobileui.injection.module

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.mobileui.MainActivity
import com.adityakamble49.wordlist.mobileui.UIThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * UI Module
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}