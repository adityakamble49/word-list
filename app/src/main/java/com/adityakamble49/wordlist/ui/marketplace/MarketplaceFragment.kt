package com.adityakamble49.wordlist.ui.marketplace

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import javax.inject.Inject

/**
 * Marketplace Fragment
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
class MarketplaceFragment : BaseInjectableFragment(), MarketplaceContract.View {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: MarketplaceContract.Presenter

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = MarketplaceFragment()
    }

    override fun getLayoutId() = R.layout.fragment_marketplace

    override fun bindViewOnCreate() {}

    override fun bindView() {
    }

    override fun initializePresenter() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MarketplaceViewModel::class.java)
        presenter.setViewModel(viewModel)
        presenter.initialize()
    }
}