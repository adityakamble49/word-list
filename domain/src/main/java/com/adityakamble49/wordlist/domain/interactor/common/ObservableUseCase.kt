package com.adityakamble49.wordlist.domain.interactor.common

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Flowable Use Case
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
abstract class ObservableUseCase<T, in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    private var disposables = CompositeDisposable()

    abstract fun buildUseCaseObservable(param: Params? = null): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    private fun addDisposable(disposable: Disposable) {
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}