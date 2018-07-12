package com.adityakamble49.wordlist.cache.dao

import android.arch.persistence.room.*
import com.adityakamble49.wordlist.cache.model.CachedMarketplaceWordList
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Cached Marketplace Word List Dao
 *
 * @author Aditya Kamble
 * @since 12/7/2018
 */
@Dao
interface CachedMarketplaceWordListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marketplaceWordList: CachedMarketplaceWordList): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(listOfMarketplaceWordList: List<CachedMarketplaceWordList>): List<Long>

    @Update
    fun updateList(marketplaceWordList: CachedMarketplaceWordList)

    @Query("SELECT * FROM marketplace_wordlist")
    fun getMarketplaceWordLists(): Flowable<List<CachedMarketplaceWordList>>

    @Query("SELECT * FROM marketplace_wordlist WHERE name = :name")
    fun getMarketplaceWordListByName(name: String): Single<CachedMarketplaceWordList>

    @Delete
    fun deleteList(marketplaceWordList: CachedMarketplaceWordList)
}