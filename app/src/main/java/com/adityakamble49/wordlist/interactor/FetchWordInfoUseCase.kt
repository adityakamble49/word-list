package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.model.DictionaryWord
import com.adityakamble49.wordlist.remote.WordListService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Fetch Word Information Use Case
 *
 * @author Aditya Kamble
 * @since 14/6/2018
 */
class FetchWordInfoUseCase @Inject constructor(
        private val wordListService: WordListService) {

    private fun buildUseCaseObservable(word: String) = wordListService.getWordInformation(word)

    fun execute(word: String): Observable<List<DictionaryWord>> {
        return buildUseCaseObservable(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}