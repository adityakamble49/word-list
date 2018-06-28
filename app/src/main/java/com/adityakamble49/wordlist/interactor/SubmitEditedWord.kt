package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Submit Word Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class SubmitEditedWord @Inject constructor(
        private val wordRepo: WordRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(editedWord: Word): Completable {
        return Completable.create { e ->
            wordRepo.updateWord(editedWord)
            e.onComplete()
        }
    }

    fun execute(newWord: Word, observer: DisposableCompletableObserver) {
        val observable = buildUseCaseObservable(newWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}