package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Word Cache Interface
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */
interface MarketplaceWordListCache {

    fun saveMarketplaceWordList(marketplaceWordListEntity: MarketplaceWordListEntity): Completable

    fun saveMarketplaceWordLists(marketplaceWordLists: List<MarketplaceWordListEntity>): Completable

    fun updateList(marketplaceWordList: MarketplaceWordListEntity)

    fun deleteList(marketplaceWordList: MarketplaceWordListEntity)

    fun getMarketplaceWordLists(): Observable<List<MarketplaceWordListEntity>>
}