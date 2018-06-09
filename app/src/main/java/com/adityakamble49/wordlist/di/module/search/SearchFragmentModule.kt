package com.adityakamble49.wordlist.di.module.search

import com.adityakamble49.wordlist.ui.search.SearchContract
import com.adityakamble49.wordlist.ui.search.SearchFragment
import com.adityakamble49.wordlist.ui.search.SearchPresenter
import dagger.Binds
import dagger.Module

/**
 * Search Fragment Module
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
@Module
abstract class SearchFragmentModule {

    @Binds
    abstract fun provideSearchFragmentView(searchFragment: SearchFragment): SearchContract.View

    @Binds
    abstract fun provideSearchPresenter(searchPresenter: SearchPresenter): SearchContract.Presenter
}