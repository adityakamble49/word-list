package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordDao
import com.adityakamble49.wordlist.cache.db.WordListDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Delete Word List Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class DeleteWordList @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordListDao: WordListDao,
        private val wordDao: WordDao) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val wordListName = wordListDao.getWordListByIdDirect(currentWordListId).name
            if (wordListName == "Sample Word List") {
                throw IllegalArgumentException()
            }
            wordListDao.deleteById(currentWordListId)
            wordDao.deleteWordsOf(currentWordListId)
            if (wordListDao.getWordListsDirect().isNotEmpty()) {
                preferenceHelper.currentLoadedListId = wordListDao.getWordListsDirect()[0].id
            } else {
                preferenceHelper.currentLoadedListId = 0
            }
            e.onComplete()
        }
    }

    fun execute(observer: DisposableCompletableObserver) {
        val observable = buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}