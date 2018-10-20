package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Means Like View Model
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
class RelatedWordBasicViewModel @Inject constructor(
        private val wordListService: WordListService) : ViewModel() {

    var searchPublishSubject: PublishSubject<String> = PublishSubject.create()
    var relatedWordBasicList: MutableLiveData<List<RelatedWordBasic>> = MutableLiveData()
    private val relatedWordsBasicSubscriber = RelatedWordsBasicSubscriber()

    init {
        fetchRelatedWordBasic()
    }

    private fun fetchRelatedWordBasic() {
        val subscriber = searchPublishSubject.debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .filter { return@filter it.isNotEmpty() }
                .observeOn(Schedulers.io())
                .subscribeWith(object : Observer<String> {
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onError(e: Throwable) {}

                    override fun onNext(t: String) {
                        val relatedSubscriber = wordListService.getRelatedWordBasic(
                                RemoteUrls.getRelatedWordsBasicUrl(t))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(relatedWordsBasicSubscriber)
                    }
                })
    }

    private inner class RelatedWordsBasicSubscriber : SingleObserver<List<RelatedWordBasic>> {

        override fun onSuccess(t: List<RelatedWordBasic>) {
            relatedWordBasicList.postValue(t)
        }

        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}
    }
}