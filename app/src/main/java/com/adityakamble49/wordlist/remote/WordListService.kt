package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.model.RelatedWordAdjective
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.model.RelatedWordDescribe
import com.adityakamble49.wordlist.model.RelatedWordTriggered
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Word List Remote service
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
interface WordListService {

    @GET
    fun getRelatedWordBasic(@Url relatedWordsBasicUrl: String): Single<List<RelatedWordBasic>>

    @GET
    fun getRelatedWordDescribe(@Url relatedWordsDescribeUrl: String): Single<List<RelatedWordDescribe>>

    @GET
    fun getRelatedWordAdjective(@Url relatedWordsAdjectiveUrl: String): Single<List<RelatedWordAdjective>>

    @GET
    fun getRelatedWordTriggered(@Url relatedWordsTriggeredUrl: String): Single<List<RelatedWordTriggered>>
}