package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.Status
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Related Word Common View Model
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
abstract class RelatedWordsCommonViewModel<T> : ViewModel() {

    var searchPublishSubject: PublishSubject<String> = PublishSubject.create()
    var relatedWordList: MutableLiveData<List<T>> = MutableLiveData()
    var requestStatus: MutableLiveData<Status> = MutableLiveData()
    protected val relatedWordSubscriber = RelatedWordSubscriber()
    private var currentQuery: String = ""

    init {
        fetchRelatedWordBasic()
    }

    private fun fetchRelatedWordBasic() {
        val subscriber = searchPublishSubject.debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .filter { return@filter it.isNotEmpty() && it != currentQuery }
                .observeOn(Schedulers.io())
                .subscribeWith(object : Observer<String> {
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onError(e: Throwable) {}

                    override fun onNext(t: String) {
                        currentQuery = t
                        requestStatus.postValue(Status.RUNNING)
                        val wordSubscriber = performRelatedWordRequest(t)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(relatedWordSubscriber)
                    }
                })
    }

    abstract fun performRelatedWordRequest(query: String): Single<List<T>>

    protected inner class RelatedWordSubscriber : SingleObserver<List<T>> {

        override fun onSuccess(t: List<T>) {
            requestStatus.postValue(Status.SUCCESS)
            relatedWordList.postValue(t)
        }

        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {
            requestStatus.postValue(Status.FAILED)
            Timber.e(e)
        }
    }
}