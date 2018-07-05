package com.adityakamble49.wordlist.remote.service

import com.adityakamble49.wordlist.remote.utils.Constants.RemoteUrls
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Word List Service Factory
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
object WordListServiceFactory {

    fun makeWordListService(okHttpClient: OkHttpClient): WordListService {
        return makeQuotesService(okHttpClient, makeGson())
    }

    private fun makeQuotesService(okHttpClient: OkHttpClient, gson: Gson): WordListService {
        val retrofit = Retrofit.Builder()
                .baseUrl(RemoteUrls.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(WordListService::class.java)
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    fun makeOkHttpClient(debug: Boolean): OkHttpClient {
        return makeOkHttpClient(makeLoggingInterceptor(debug))
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .cache(null)
                .build()
    }

    private fun makeLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = when (debug) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}