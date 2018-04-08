package com.adityakamble49.wordlist.di.module.wordviewpager

import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.ui.word.WordContract
import dagger.Binds
import dagger.Module

/**
 * Word View Pager View Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
abstract class WordViewPagerActivityViewModule {

    @Binds
    abstract fun provideWordViewPagerView(
            wordActivity: WordActivity): WordContract.View
}