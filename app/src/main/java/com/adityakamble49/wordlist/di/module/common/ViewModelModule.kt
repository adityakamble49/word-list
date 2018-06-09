package com.adityakamble49.wordlist.di.module.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.ui.common.WordListViewModelFactory
import com.adityakamble49.wordlist.ui.list.WordListViewModel
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
import com.adityakamble49.wordlist.ui.search.SearchViewModel
import com.adityakamble49.wordlist.ui.word.WordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * View Model Module
 *
 * @author Aditya Kamble
 * @since 15/4/2018
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(
            mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordListViewModel::class)
    abstract fun bindWordListViewModel(wordListViewModel: WordListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordViewModel::class)
    abstract fun bindWordViewModel(wordViewModel: WordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(
            wordListViewModelFactory: WordListViewModelFactory): ViewModelProvider.Factory
}