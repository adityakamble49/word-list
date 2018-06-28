package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.utils.Constants
import com.adityakamble49.wordlist.utils.getMnemonicAPIUrl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
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
class FetchMnemonic @Inject constructor(
        private val okHttpClient: OkHttpClient) : BaseRxUseCase() {

    private fun buildUseCaseObservable(word: String): Single<String> {
        return Single.create { e ->
            val mnemonicRequest = Request.Builder()
                    .url(getMnemonicAPIUrl(word))
                    .build()
            val mnemonicResponseHtml = okHttpClient.newCall(
                    mnemonicRequest).execute().body()?.string()
            val doc = Jsoup.parse(mnemonicResponseHtml)
            doc.select(Constants.JSoupQueries.MNEMONIC_ATTRIBUTION).remove()
            val mnemonicsList = doc.select(Constants.JSoupQueries.MNEMONIC_CARD)
            val sb = StringBuilder()
            mnemonicsList.forEachIndexed { index, element ->
                sb.append("${index + 1}. ${element.text()}")
                if (index + 1 != mnemonicsList.size) {
                    sb.append("\n\n")
                }
            }
            e.onSuccess(sb.toString())
        }
    }

    fun execute(word: String, observer: DisposableSingleObserver<String>) {
        val observable = buildUseCaseObservable(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}