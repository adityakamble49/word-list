package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.MarketplaceWordListEntity
import com.adityakamble49.wordlist.remote.mapper.MarketplaceWordListMapper
import com.adityakamble49.wordlist.remote.model.MarketplaceWordList
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * MarketplaceWordList Remote Implementation Test
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
@RunWith(JUnit4::class)
class MarketplaceWordListRemoteImplTest {

    @Mock private lateinit var wordListService: WordListService
    @Mock private lateinit var mapper: MarketplaceWordListMapper
    private lateinit var marketplaceWordListRemoteImpl: MarketplaceWordListRemoteImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        marketplaceWordListRemoteImpl = MarketplaceWordListRemoteImpl(wordListService, mapper)
    }

    @Test
    fun getMarketplaceWordListCompletes() {
        stubWordListServiceGetMarketplaceWordList(
                Single.just(RemoteDataFactory.makeListOfMarketplaceWordList(4)))
        stubMarketplaceWordListMapper(any(), RemoteDataFactory.makeMarketplaceWordListEntity())
        val testObserver = marketplaceWordListRemoteImpl.getMarketplaceWordList().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMarketplaceWordListCallsServer() {
        stubWordListServiceGetMarketplaceWordList(
                Single.just(RemoteDataFactory.makeListOfMarketplaceWordList(4)))
        stubMarketplaceWordListMapper(any(), RemoteDataFactory.makeMarketplaceWordListEntity())
        marketplaceWordListRemoteImpl.getMarketplaceWordList().test()
        verify(wordListService).getMarketplaceWordList(any(), any())
    }

    @Test
    fun getMarketplaceWordListReturnsData() {
        // Stub Marketplace Word List response
        val marketplaceWordList = RemoteDataFactory.makeListOfMarketplaceWordList(4)
        stubWordListServiceGetMarketplaceWordList(Single.just(marketplaceWordList))

        // Stub Marketplace Word List Mapping to Entity
        val entities = mutableListOf<MarketplaceWordListEntity>()
        marketplaceWordList.forEach {
            val entity = RemoteDataFactory.makeMarketplaceWordListEntity()
            entities.add(entity)
            stubMarketplaceWordListMapper(it, entity)
        }

        // Test getMarketplaceWordList
        val testObserver = marketplaceWordListRemoteImpl.getMarketplaceWordList().test()
        testObserver.assertValues(entities)
    }

    private fun stubWordListServiceGetMarketplaceWordList(
            single: Single<List<MarketplaceWordList>>) {
        whenever(wordListService.getMarketplaceWordList(any(), any())).thenReturn(single)
    }

    private fun stubMarketplaceWordListMapper(model: MarketplaceWordList,
                                              entity: MarketplaceWordListEntity) {
        whenever(mapper.mapFromModel(model)).thenReturn(entity)
    }
}