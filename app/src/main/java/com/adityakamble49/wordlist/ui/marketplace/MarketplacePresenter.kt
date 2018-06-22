package com.adityakamble49.wordlist.ui.marketplace

import android.arch.lifecycle.Observer
import com.adityakamble49.wordlist.cache.db.MarketplaceWordListDao
import com.adityakamble49.wordlist.interactor.RefreshMarketplaceWordList
import com.adityakamble49.wordlist.model.MarketplaceWordList
import javax.inject.Inject

/**
 * Marketplace Presenter
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */

class MarketplacePresenter @Inject constructor(
        private val view: MarketplaceContract.View,
        private val marketplaceWordListDao: MarketplaceWordListDao,
        private val refreshMarketplaceWordList: RefreshMarketplaceWordList) :
        MarketplaceContract.Presenter {

    private lateinit var viewModel: MarketplaceViewModel

    override fun initialize() {
        observeMarketplaceWordList()
        refreshMarketplaceWordList.execute().subscribe { view.showMessage("Marketplace Refreshed") }
    }

    override fun setViewModel(viewModel: MarketplaceViewModel) {
        this.viewModel = viewModel
    }

    private fun observeMarketplaceWordList() {
        view.showLoading(true)
        viewModel.listOfMarketplaceWordList = marketplaceWordListDao.getMarketplaceWordList()
        viewModel.listOfMarketplaceWordList.observe(view, Observer {
            view.showLoading(false)
            it?.let { view.updateMarketplaceList(it) }
        })
    }

    override fun onClickDownload(marketplaceWordList: MarketplaceWordList) {
        // TODO Start download use case
    }
}