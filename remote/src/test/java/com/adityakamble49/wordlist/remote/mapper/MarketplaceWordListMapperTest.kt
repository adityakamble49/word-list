package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import com.adityakamble49.wordlist.data.model.MarketplaceWordListStatus
import com.adityakamble49.wordlist.remote.model.MarketplaceWordList
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * MarketplaceWordList Mapper Test
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class MarketplaceWordListMapperTest {

    private val mapper = MarketplaceWordListMapper()

    @Test
    fun mapFromModelTest() {
        val model = RemoteDataFactory.makeMarketplaceWordList()
        val entity = mapper.mapFromModel(model)
        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: MarketplaceWordList, entity: MarketplaceWordListEntity) {
        assertEquals(model.name, entity.name)
        assertEquals(model.sha, entity.sha)
        assertEquals(model.downloadUrl, entity.downloadUrl)
        assertEquals(MarketplaceWordListStatus.NOT_AVAILABLE, entity.status)
    }
}