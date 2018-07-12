package com.adityakamble49.wordlist.cache.dao

import com.adityakamble49.wordlist.cache.test.DataFactory.randomString
import com.adityakamble49.wordlist.cache.test.MarketplaceWordListDataFactory.makeCachedMarketplaceWordList
import com.adityakamble49.wordlist.cache.test.MarketplaceWordListDataFactory.makeCachedMarketplaceWordLists
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Cached MarketplaceWordList Dao Test
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@RunWith(RobolectricTestRunner::class)
class CachedMarketplaceWordListDaoTest : DaoTest() {

    private val dao = database.cachedMarketplaceWordListDao()

    @Test
    fun insertMarketplaceWordListReturnId() {
        val marketplaceWordList = makeCachedMarketplaceWordList()
        val marketplaceWordListId = dao.insert(marketplaceWordList)
        assertNotNull(marketplaceWordListId)
    }

    @Test
    fun insertMarketplaceWordListsReturnIds() {
        val marketplaceWordLists = makeCachedMarketplaceWordLists(10)
        val marketplaceWordListIds = dao.insertList(marketplaceWordLists)
        assertNotNull(marketplaceWordListIds)
    }

    @Test
    fun updateMarketplaceWordListCompletes() {
        // Insert Marketplace Word List
        val marketplaceWordList = makeCachedMarketplaceWordList()
        dao.insert(marketplaceWordList)

        // Update List
        marketplaceWordList.sha = randomString()
        dao.updateList(marketplaceWordList)

        // Fetch and assert updated
        val testObserver = dao.getMarketplaceWordListByName(marketplaceWordList.name).test()
        testObserver.assertValue(marketplaceWordList)
    }

    @Test
    fun deleteMarketplaceWordListCompletes() {
        // Insert Marketplace Word List
        val marketplaceWordList = makeCachedMarketplaceWordList()
        dao.insert(marketplaceWordList)

        // Delete list
        dao.deleteList(marketplaceWordList)

        // Fetch with name ans assert empty
        val testObserver = dao.getMarketplaceWordListByName(marketplaceWordList.name).test()
        testObserver.assertNotComplete()
    }
}