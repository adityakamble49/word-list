package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.model.DictionaryWord
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Fetch Word Information Use Case
 *
 * @author Aditya Kamble
 * @since 14/6/2018
 */
class FetchWordInfo @Inject constructor(
        private val wordListService: WordListService) : BaseRxUseCase() {

    private fun buildUseCaseObservable(word: String) = wordListService.getWordInformation(word)

    fun execute(word: String, observer: DisposableSingleObserver<List<DictionaryWord>>) {
        val observable = buildUseCaseObservable(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}