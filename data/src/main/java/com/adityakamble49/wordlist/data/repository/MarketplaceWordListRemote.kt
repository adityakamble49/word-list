package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import io.reactivex.Single

/**
 * Marketplace WordList Remote Interface
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
interface MarketplaceWordListRemote {

    fun getMarketplaceWordList(): Single<List<MarketplaceWordListEntity>>
}