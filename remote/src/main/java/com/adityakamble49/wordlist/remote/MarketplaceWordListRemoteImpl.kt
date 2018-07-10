package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import com.adityakamble49.wordlist.data.repository.MarketplaceWordListRemote
import com.adityakamble49.wordlist.remote.mapper.MarketplaceWordListMapper
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.utils.Constants.RemoteUrls
import io.reactivex.Single
import javax.inject.Inject

/**
 * MarketplaceWordList Remote Implementation
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class MarketplaceWordListRemoteImpl @Inject constructor(
        private val wordListService: WordListService,
        private val mapper: MarketplaceWordListMapper) : MarketplaceWordListRemote {

    override fun getMarketplaceWordLists(): Single<List<MarketplaceWordListEntity>> {
        return wordListService.getMarketplaceWordList(RemoteUrls.GITHUB_WORDLIST,
                "token ${RemoteUrls.GITHUB_AUTH_TOKEN}")
                .map { it.map { mapper.mapFromModel(it) } }
    }
}