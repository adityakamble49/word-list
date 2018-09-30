package com.adityakamble49.wordlist.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.di.common.ViewModelKey
import com.adityakamble49.wordlist.ui.common.WordListViewModelFactory
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Presentation Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindWordListViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(
            wordListViewModelFactory: WordListViewModelFactory): ViewModelProvider.Factory
}