package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.model.WordList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Build Get Current Word List UseCase
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class GetCurrentWordListUseCase @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseObservable(): Observable<WordList> {
        return Observable.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val currentWordList = wordListRepo.getWordListById(currentWordListId)
            e.onNext(currentWordList)
            e.onComplete()
        }
    }

    fun execute(): Observable<WordList> {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}