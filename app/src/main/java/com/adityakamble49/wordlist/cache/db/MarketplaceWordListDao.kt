package com.adityakamble49.wordlist.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.adityakamble49.wordlist.model.MarketplaceWordList

/**
 * Marketplace Word List Dao
 *
 * @author Aditya Kamble
 * @since 22/6/2018
 */
@Dao
interface MarketplaceWordListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marketplaceWordList: MarketplaceWordList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(listOfMarketplaceWordList: List<MarketplaceWordList>)

    @Query("SELECT * FROM marketplace_wordlist")
    fun getMarketplaceWordList(): LiveData<List<MarketplaceWordList>>
}