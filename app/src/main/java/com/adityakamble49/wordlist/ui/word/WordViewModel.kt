package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Word View Model
 *
 * @author Aditya Kamble
 * @since 23/10/2018
 */
class WordViewModel @Inject constructor(
        private val wordListService: WordListService) : ViewModel() {

    val wordInfo: MutableLiveData<Word> = MutableLiveData()
    private val wordInfoSubscriber = WordInfoSubscriber()

    fun fetchWordInfo(wordName: String) {
        val subscription = wordListService.getWordInfo(RemoteUrls.getWordKeyVal(wordName),
                RemoteUrls.WORD_INFO_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(wordInfoSubscriber)
    }

    private inner class WordInfoSubscriber : SingleObserver<List<Word>> {

        override fun onSuccess(t: List<Word>) {
            if (t.isNotEmpty()) {
                wordInfo.postValue(t[0])
            }
        }

        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}
    }
}