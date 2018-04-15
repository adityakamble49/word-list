package com.adityakamble49.wordlist.di.module.list

import com.adityakamble49.wordlist.ui.list.WordListContract
import com.adityakamble49.wordlist.ui.list.WordListFragment
import com.adityakamble49.wordlist.ui.list.WordListPresenter
import dagger.Binds
import dagger.Module

/**
 * Word List Fragment View Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
abstract class WordListFragmentModule {

    @Binds
    abstract fun provideWordListFragmentView(
            wordListFragment: WordListFragment): WordListContract.View

    @Binds
    abstract fun provideWordListPresenter(
            wordListPresenter: WordListPresenter): WordListContract.Presenter
}