package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.entities.MarketplaceWordList
import io.reactivex.Flowable

/**
 * Marketplace Word List Dao
 *
 * @author Aditya Kamble
 * @since 15/10/2018
 */
@Dao
interface MarketplaceWordListDao {

    @Insert
    fun insert(marketplaceWordList: MarketplaceWordList): Long

    @Insert
    fun insertList(listOfMarketplaceWordList: List<MarketplaceWordList>): List<Long>

    @Update
    fun update(marketplaceWordList: MarketplaceWordList): Int

    @Delete
    fun delete(marketplaceWordList: MarketplaceWordList): Int

    @Query("SELECT * FROM marketplace_wordlist")
    fun getMarketplaceWordList(): Flowable<List<MarketplaceWordList>>

    @Query("SELECT  * FROM marketplace_wordlist WHERE sha= :hash")
    fun getMarketplaceWordListByHash(hash: String): Flowable<MarketplaceWordList>
}