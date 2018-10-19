package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.ui.related.RelatedWordBasicFragment
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
    abstract fun contributesRelatedWordBasicsFragment(): RelatedWordBasicFragment
}