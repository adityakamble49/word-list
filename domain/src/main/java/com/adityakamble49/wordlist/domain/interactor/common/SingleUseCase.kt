package com.adityakamble49.wordlist.domain.interactor.common

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Single Use Case
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
abstract class SingleUseCase<T, in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    private var disposables = CompositeDisposable()

    abstract fun buildSingleUseCase(param: Params? = null): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, params: Params? = null) {
        val observable = buildSingleUseCase(params)
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