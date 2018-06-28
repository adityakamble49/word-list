package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordListRepo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save LastWordId For WordList UseCase
 *
 * @author Aditya Kamble
 * @since 10/4/2018
 */
class SaveLastWordIdForWordList @Inject constructor(
        private val wordListRepo: WordListRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(wordListId: Int, lastWordId: Int): Completable {
        return Completable.create { e ->
            wordListRepo.updateLastWordIdForWordList(wordListId, lastWordId)
            e.onComplete()
        }
    }

    fun execute(wordListId: Int, lastWordId: Int, observer: DisposableCompletableObserver) {
        val observable = buildUseCaseObservable(wordListId, lastWordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}