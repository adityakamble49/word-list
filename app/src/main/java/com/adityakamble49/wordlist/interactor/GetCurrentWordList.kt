package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.model.WordList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Build Get Current Word List UseCase
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class GetCurrentWordList @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordListRepo: WordListRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Single<WordList> {
        return Single.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val currentWordList = wordListRepo.getWordListById(currentWordListId)
            e.onSuccess(currentWordList)
        }
    }

    fun execute(observer: DisposableSingleObserver<WordList>) {
        val observable = buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}