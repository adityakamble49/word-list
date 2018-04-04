package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.ui.list.WordListViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Module
class WordListFragmentModule {

    @Provides
    fun provideWordListViewModelFactory() = WordListViewModelFactory()

}