package com.adityakamble49.wordlist.di.module.word

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.ui.word.WordViewModelFactory
import dagger.Module
import dagger.Provides


/**
 * Word Activity Module
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
@Module
class WordActivityModule {

    @Provides
    fun provideWordViewModelFactory(wordRepo: WordRepo) = WordViewModelFactory(wordRepo)
}