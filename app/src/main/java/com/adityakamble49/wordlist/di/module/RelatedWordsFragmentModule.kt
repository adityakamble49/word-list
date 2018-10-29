package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.ui.related.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Main Fragments Module
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
@Module
abstract class RelatedWordsFragmentModule {

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordBasicsFragment(): RelatedWordsBasicFragment

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordAntonymFragment(): RelatedWordsAntonymFragment

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordDescribeFragment(): RelatedWordsDescribeFragment

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordAdjectiveFragment(): RelatedWordsAdjectiveFragment

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordTriggeredFragment(): RelatedWordsTriggeredFragment

    @ContributesAndroidInjector()
    abstract fun contributesRelatedWordRhymingFragment(): RelatedWordsRhymingFragment
}