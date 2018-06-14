package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.model.DictionaryWord
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Word List service
 *
 * @author Aditya Kamble
 * @since 12/6/2018
 */
interface WordListService {

    @GET("dictionary/{word}")
    fun getWordInformation(@Path("word") word: String): Observable<List<DictionaryWord>>

}