package com.adityakamble49.wordlist.di.module.main

import com.adityakamble49.wordlist.cache.db.WordRepo
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
    fun provideWordListViewModelFactory(wordRepo: WordRepo) = WordListViewModelFactory(wordRepo)

}