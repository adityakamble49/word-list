package com.adityakamble49.wordlist.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.di.common.ViewModelKey
import com.adityakamble49.wordlist.ui.common.WordListViewModelFactory
import com.adityakamble49.wordlist.ui.main.MainActivityViewModel
import com.adityakamble49.wordlist.ui.related.*
import com.adityakamble49.wordlist.ui.word.WordViewModel
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
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsViewModel::class)
    abstract fun bindRelatedWordsViewModel(
            relatedWordsViewModel: RelatedWordsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsBasicViewModel::class)
    abstract fun bindRelatedWordBasicViewModel(
            relatedWordBasicViewModel: RelatedWordsBasicViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsAntonymViewModel::class)
    abstract fun bindRelatedWordAntonymViewModel(
            relatedWordsAntonymViewModel: RelatedWordsAntonymViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsDescribeViewModel::class)
    abstract fun bindRelatedWordDescribeViewModel(
            relatedWordsDescribeViewModel: RelatedWordsDescribeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsAdjectiveViewModel::class)
    abstract fun bindRelatedWordAdjectiveViewModel(
            relatedWordsAdjectiveViewModel: RelatedWordsAdjectiveViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelatedWordsTriggeredViewModel::class)
    abstract fun bindRelatedWordTriggeredViewModel(
            relatedWordsTriggeredViewModel: RelatedWordsTriggeredViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordViewModel::class)
    abstract fun bindWordViewModel(wordViewModel: WordViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(
            wordListViewModelFactory: WordListViewModelFactory): ViewModelProvider.Factory
}