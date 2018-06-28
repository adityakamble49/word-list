package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get Current Words Use Case
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class GetCurrentWords @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo,
        private val wordListRepo: WordListRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Single<List<Word>> {
        return Single.create { e ->
            val currentLoadedListId = preferenceHelper.currentLoadedListId
            val currentWordList = wordListRepo.getWordListById(currentLoadedListId)
            val wordList = wordRepo.getWordListDirect(currentWordList.id)
            e.onSuccess(wordList)
        }
    }

    fun execute(observer: DisposableSingleObserver<List<Word>>) {
        val observable = buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}