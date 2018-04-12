package com.adityakamble49.wordlist.di.module.word

import com.adityakamble49.wordlist.ui.word.WordActivity
import com.adityakamble49.wordlist.ui.word.WordContract
import com.adityakamble49.wordlist.ui.word.WordPresenter
import dagger.Binds
import dagger.Module

/**
 * [WordActivity] View Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
abstract class WordActivityViewModule {

    @Binds
    abstract fun provideWordActivityView(wordActivity: WordActivity): WordContract.View

    @Binds
    abstract fun provideWordActivityPresenter(wordPresenter: WordPresenter): WordContract.Presenter
}