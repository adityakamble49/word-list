package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import com.adityakamble49.wordlist.data.model.MarketplaceWordListStatus
import com.adityakamble49.wordlist.remote.model.MarketplaceWordList
import javax.inject.Inject

/**
 * Marketplace Word List Mapper
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class MarketplaceWordListMapper @Inject constructor() :
        Mapper<MarketplaceWordList, MarketplaceWordListEntity> {

    override fun mapFromModel(model: MarketplaceWordList): MarketplaceWordListEntity {
        return MarketplaceWordListEntity(model.name, model.sha, model.downloadUrl,
                MarketplaceWordListStatus.NOT_AVAILABLE)
    }
}