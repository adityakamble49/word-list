package com.adityakamble49.wordlist.ui.marketplace

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableFragment
import com.adityakamble49.wordlist.utils.gone
import com.adityakamble49.wordlist.utils.visible
import kotlinx.android.synthetic.main.fragment_marketplace.view.*
import javax.inject.Inject

/**
 * Marketplace Fragment
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
class MarketplaceFragment : BaseInjectableFragment(), MarketplaceContract.View,
        AdapterView.OnItemClickListener {

    // Dagger Injected Fields
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var presenter: MarketplaceContract.Presenter

    // View Fields
    private lateinit var marketplaceListAdapter: MarketplaceListAdapter

    /*
     * Lifecycle Functions
     */

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (view?.id) {
            R.id.item_download_image -> presenter.onClickDownload(
                    marketplaceListAdapter.itemList[position])
        }
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = MarketplaceFragment()
    }

    override fun getLayoutId() = R.layout.fragment_marketplace

    override fun bindViewOnCreate() {}

    override fun bindView() {
        with(rootView) {

            // Setup Marketplace list
            marketplaceListAdapter = MarketplaceListAdapter()
            marketplaceListAdapter.onItemClickListener = this@MarketplaceFragment
            val linearLayoutManager = LinearLayoutManager(context)
            val decorator = DividerItemDecoration(context, linearLayoutManager.orientation)
            recyclerview_marketplace.adapter = marketplaceListAdapter
            recyclerview_marketplace.layoutManager = linearLayoutManager
            recyclerview_marketplace.addItemDecoration(decorator)
        }
    }

    override fun initializePresenter() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MarketplaceViewModel::class.java)
        presenter.setViewModel(viewModel)
        presenter.initialize()
    }

    override fun showLoading(toShow: Boolean) {
        if (toShow) {
            rootView.progress_marketplace.visible()
        } else {
            rootView.progress_marketplace.gone()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateMarketplaceList(marketplaceWordList: List<MarketplaceWordList>) {
        toggleEmptyView(marketplaceWordList.size)
        marketplaceListAdapter.itemList = marketplaceWordList
        marketplaceListAdapter.notifyDataSetChanged()
    }

    private fun toggleEmptyView(size: Int) {
        if (size > 0) {
            rootView.ll_empty_view.gone()
        } else {
            rootView.ll_empty_view.visible()
        }
    }

}