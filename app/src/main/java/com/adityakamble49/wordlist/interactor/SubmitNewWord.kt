package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
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
class SubmitNewWord @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordListRepo: WordListRepo,
        private val wordRepo: WordRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(newWord: Word): Completable {
        return Completable.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val currentWordList = wordListRepo.getWordListById(currentWordListId)
            newWord.listId = currentWordList.id
            wordRepo.insertWord(newWord)
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