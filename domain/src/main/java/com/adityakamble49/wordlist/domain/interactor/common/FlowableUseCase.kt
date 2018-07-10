package com.adityakamble49.wordlist.domain.interactor.common

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Flowable Use Case
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */
abstract class FlowableUseCase<T, in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    private var disposables = CompositeDisposable()

    abstract fun buildUseCaseFlowable(param: Params? = null): Flowable<T>

    fun execute(observer: DisposableSubscriber<T>, params: Params? = null) {
        val flowable = buildUseCaseFlowable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)
        addDisposable(flowable.subscribeWith(observer))
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