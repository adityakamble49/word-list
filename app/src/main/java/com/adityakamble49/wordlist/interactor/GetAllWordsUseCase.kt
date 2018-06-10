package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get All Words Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class GetAllWordsUseCase @Inject constructor(
        private val wordRepo: WordRepo) {

    private fun buildUseCaseObservable(): Observable<List<Word>> {
        return Observable.create { e ->
            val allWordList = wordRepo.getAllWords()
            e.onNext(allWordList)
            e.onComplete()
        }
    }

    fun execute(): Observable<List<Word>> {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}