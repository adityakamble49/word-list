package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.utils.DataProcessor
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Import Word List to DB UseCase
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class ImportWordListToDatabase @Inject constructor(
        private val dataProcessor: DataProcessor,
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo,
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { importWordListToDBEmitter ->

            // Create Shuffled Word List
            val sampleWordList = WordList(0, UUID.randomUUID().toString(), "",
                    "Sample Word List", 1)
            val sampleWordListId = wordListRepo.insert(sampleWordList)

            // Insert Words into DB
            val wordListSample = dataProcessor.parseWordList()
            wordListSample.forEach {
                it.listId = sampleWordListId.toInt()
            }
            wordRepo.insertWords(wordListSample)

            // Fetch inserted words with updated primary key
            val wordListSampleFetched = wordRepo.getWordListDirect(sampleWordListId.toInt())

            // Update wordList with last word Id
            sampleWordList.lastWordId = wordListSampleFetched[0].id
            wordListRepo.updateWordList(sampleWordList)

            preferenceHelper.areWordsImported = true
            importWordListToDBEmitter.onComplete()
        }
    }

    fun execute(): Completable {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}