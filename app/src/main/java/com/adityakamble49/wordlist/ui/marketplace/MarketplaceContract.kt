package com.adityakamble49.wordlist.ui.marketplace

import android.arch.lifecycle.LifecycleOwner
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.ui.common.BaseContract

/**
 * Marketplace Contract
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
interface MarketplaceContract {

    interface View : BaseContract.View, LifecycleOwner {
        fun showLoading(toShow: Boolean)
        fun showMessage(message: String)
        fun updateMarketplaceList(marketplaceWordList: List<MarketplaceWordList>)
    }

    interface Presenter : BaseContract.Presenter {
        fun setViewModel(viewModel: MarketplaceViewModel)
        fun onClickDownload(marketplaceWordList: MarketplaceWordList)
    }
}