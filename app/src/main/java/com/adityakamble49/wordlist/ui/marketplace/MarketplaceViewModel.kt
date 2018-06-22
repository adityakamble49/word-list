package com.adityakamble49.wordlist.ui.marketplace

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.MarketplaceWordList
import javax.inject.Inject

/**
 * Marketplace ViewModel
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
class MarketplaceViewModel @Inject constructor() : ViewModel() {

    lateinit var listOfMarketplaceWordList: LiveData<List<MarketplaceWordList>>
}