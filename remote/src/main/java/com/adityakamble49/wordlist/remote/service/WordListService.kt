package com.adityakamble49.wordlist.remote.service

import com.adityakamble49.wordlist.remote.model.DictionaryWord
import com.adityakamble49.wordlist.remote.model.MarketplaceWordList
import com.adityakamble49.wordlist.remote.model.Word
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Word List service
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
interface WordListService {

    @GET("dictionary/{word}")
    fun getWordInformation(@Path("word") word: String): Single<List<DictionaryWord>>

    @GET
    fun getMarketplaceWordList(@Url marketplaceWordListUrl: String, @Header(
            "Authorization") authHeader: String): Single<List<MarketplaceWordList>>

    @GET
    fun getWordsFromMarketplace(@Url wordListUrl: String, @Header(
            "Authorization") authHeader: String): Single<List<Word>>

}