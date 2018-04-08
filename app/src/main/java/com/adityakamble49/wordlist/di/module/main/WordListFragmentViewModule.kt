package com.adityakamble49.wordlist.di.module.main

import com.adityakamble49.wordlist.ui.list.WordListContract
import com.adityakamble49.wordlist.ui.list.WordListFragment
import dagger.Binds
import dagger.Module

/**
 * Word List Fragment View Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
abstract class WordListFragmentViewModule {

    @Binds
    abstract fun provideWordListFragmentView(
            wordListFragment: WordListFragment): WordListContract.View
}