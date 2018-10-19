package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Means Like View Model
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
class RelatedWordBasicViewModel @Inject constructor(
        private val wordListService: WordListService) : ViewModel() {

    var relatedWordBasicList: MutableLiveData<List<RelatedWordBasic>> = MutableLiveData()

    init {
        fetchMeansLikeWords()
    }

    private fun fetchMeansLikeWords(word: String = "lethargic") {
        val subscriber = wordListService.getRelatedWordBasic(
                RemoteUrls.getRelatedWordsBasicUrl(word))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(RelatedWordsBasicSubscriber())
    }

    private inner class RelatedWordsBasicSubscriber : SingleObserver<List<RelatedWordBasic>> {

        override fun onSuccess(t: List<RelatedWordBasic>) {
            relatedWordBasicList.postValue(t)
        }
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}
    }
}