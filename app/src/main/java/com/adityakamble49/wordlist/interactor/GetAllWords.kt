package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get All Words Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class GetAllWords @Inject constructor(
        private val wordRepo: WordRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Single<List<Word>> {
        return Single.create { e ->
            val allWordList = wordRepo.getAllWords()
            e.onSuccess(allWordList)
        }
    }

    fun execute(observer: DisposableSingleObserver<List<Word>>) {
        val observable = buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}