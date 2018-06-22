package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.model.DictionaryWord
import com.adityakamble49.wordlist.model.MarketplaceWordList
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Word List service
 *
 * @author Aditya Kamble
 * @since 12/6/2018
 */
interface WordListService {

    @GET("dictionary/{word}")
    fun getWordInformation(@Path("word") word: String): Observable<List<DictionaryWord>>

    @GET
    fun getMarketplaceWordList(@Url marketplaceWordListUrl: String, @Header(
            "Authorization") authHeader: String): Observable<List<MarketplaceWordList>>

    @GET
    fun getWordListFromMarketplace(@Url wordListUrl: String, @Header(
            "Authorization") authHeader: String): Observable<List<Word>>

}