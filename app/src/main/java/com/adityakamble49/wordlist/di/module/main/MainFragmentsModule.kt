package com.adityakamble49.wordlist.di.module.main

import com.adityakamble49.wordlist.di.module.list.WordListFragmentModule
import com.adityakamble49.wordlist.di.module.marketplace.MarketplaceFragmentModule
import com.adityakamble49.wordlist.di.module.search.SearchFragmentModule
import com.adityakamble49.wordlist.ui.list.WordListFragment
import com.adityakamble49.wordlist.ui.marketplace.MarketplaceFragment
import com.adityakamble49.wordlist.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module for Fragments in Main Activity
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Module
abstract class MainFragmentsModule {

    @ContributesAndroidInjector(modules = [(WordListFragmentModule::class)])
    abstract fun contributeWordListFragment(): WordListFragment

    @ContributesAndroidInjector(modules = [(MarketplaceFragmentModule::class)])
    abstract fun contributeMarketplaceFragment(): MarketplaceFragment

    @ContributesAndroidInjector(modules = [(SearchFragmentModule::class)])
    abstract fun contributeSearchFragment(): SearchFragment
}