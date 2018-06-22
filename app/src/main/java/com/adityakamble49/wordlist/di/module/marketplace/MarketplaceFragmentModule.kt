package com.adityakamble49.wordlist.di.module.marketplace

import com.adityakamble49.wordlist.ui.marketplace.MarketplaceContract
import com.adityakamble49.wordlist.ui.marketplace.MarketplaceFragment
import com.adityakamble49.wordlist.ui.marketplace.MarketplacePresenter
import dagger.Binds
import dagger.Module

/**
 * Marketplace Fragment View Module
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
@Module
abstract class MarketplaceFragmentModule {

    @Binds
    abstract fun provideMarketplaceFragmentView(
            marketplaceFragment: MarketplaceFragment): MarketplaceContract.View

    @Binds
    abstract fun provideMarketplacePresenter(
            marketplacePresenter: MarketplacePresenter): MarketplaceContract.Presenter
}