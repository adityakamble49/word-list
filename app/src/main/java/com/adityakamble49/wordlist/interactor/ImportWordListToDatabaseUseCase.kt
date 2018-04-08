package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.utils.DataProcessor
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Import Word List to DB UseCase
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class ImportWordListToDatabaseUseCase @Inject constructor(
        private val dataProcessor: DataProcessor,
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo) {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { e ->
            val wordList = dataProcessor.parseWordList()
            wordRepo.insertWords(wordList)
            preferenceHelper.areWordsImported = true
            e.onComplete()
        }
    }

    fun execute(): Completable {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}