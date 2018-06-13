package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.utils.Constants
import com.adityakamble49.wordlist.utils.getMnemonicAPIUrl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * Fetch Mnemonic Use Case
 *
 * @author Aditya Kamble
 * @since 13/6/2018
 */
class FetchMnemonicUseCase @Inject constructor(
        private val okHttpClient: OkHttpClient) {

    private fun buildUseCaseObservable(word: String): Observable<String> {
        return Observable.create { e ->
            val mnemonicRequest = Request.Builder()
                    .url(getMnemonicAPIUrl(word))
                    .build()
            val mnemonicResponseHtml = okHttpClient.newCall(
                    mnemonicRequest).execute().body()?.string()
            val doc = Jsoup.parse(mnemonicResponseHtml)
            val mnemonicsList = doc.select(Constants.JSoupQueries.MNEMONIC_CARD)
            val sb = StringBuilder()
            mnemonicsList.forEach { sb.append("${it.text()}\n") }
            e.onNext(sb.toString())
            e.onComplete()
        }
    }

    fun execute(word: String): Observable<String> {
        return buildUseCaseObservable(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}