package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Word List Remote service
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
interface WordListService {

    @GET(RemoteUrls.WORD_INFO_BASE_URL)
    fun getWordInfo(@Query("q") wordKeyValue: String, @Query(
            "apiKey") apiKey: String): Single<List<Word>>

    @GET
    fun getRelatedWordBasic(@Url relatedWordsBasicUrl: String): Single<List<RelatedWordBasic>>

    @GET
    fun getRelatedWordAntonym(@Url relatedWordsAntonymUrl: String): Single<List<RelatedWordAntonym>>

    @GET
    fun getRelatedWordDescribe(@Url relatedWordsDescribeUrl: String): Single<List<RelatedWordDescribe>>

    @GET
    fun getRelatedWordAdjective(@Url relatedWordsAdjectiveUrl: String): Single<List<RelatedWordAdjective>>

    @GET
    fun getRelatedWordTriggered(@Url relatedWordsTriggeredUrl: String): Single<List<RelatedWordTriggered>>

    @GET
    fun getRelatedWordRhyming(@Url relatedWordsRhymingUrl: String): Single<List<RelatedWordRhyming>>
}