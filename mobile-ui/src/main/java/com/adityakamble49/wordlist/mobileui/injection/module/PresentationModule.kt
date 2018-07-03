package com.adityakamble49.wordlist.mobileui.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.mobileui.injection.common.ViewModelKey
import com.adityakamble49.wordlist.mobileui.injection.common.WordListViewModelFactory
import com.adityakamble49.wordlist.presentation.wordlist.WordListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * View Model Module
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(WordListViewModel::class)
    abstract fun bindWordListViewModel(wordListViewModel: WordListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(
            wordListViewModelFactory: WordListViewModelFactory): ViewModelProvider.Factory
}