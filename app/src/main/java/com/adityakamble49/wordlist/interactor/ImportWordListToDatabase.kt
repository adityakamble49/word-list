package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListType
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
            val wordListManhattanEssentialShuffled = WordList(0, UUID.randomUUID().toString(), "",
                    "Manhattan Essential - Shuffled", 0, arrayListOf())
            val wordListManhattanAdvancedShuffled = WordList(0, UUID.randomUUID().toString(), "",
                    "Manhattan Advanced - Shuffled", 0, arrayListOf())
            val wordListEssentialId = wordListRepo.insert(wordListManhattanEssentialShuffled)
            val wordListEssentialUUID = wordListManhattanEssentialShuffled.hash
            val wordListAdvancedId = wordListRepo.insert(wordListManhattanAdvancedShuffled)
            val wordListAdvancedUUID = wordListManhattanAdvancedShuffled.hash


            // Insert Words into DB
            val wordListManhattanEssential = dataProcessor.parseWordList(
                    WordListType.MANHATTAN_ESSENTIAL)
            wordListManhattanEssential.forEach {
                it.listId = wordListEssentialId.toInt()
            }
            val wordListManhattanAdvanced = dataProcessor.parseWordList(
                    WordListType.MANHATTAN_ADVANCED)
            wordListManhattanAdvanced.forEach {
                it.listId = wordListAdvancedId.toInt()
            }
            wordRepo.insertWords(wordListManhattanEssential)
            wordRepo.insertWords(wordListManhattanAdvanced)

            // Fetch inserted words with updated primary key
            val wordListManhattanEssentialFetched = wordRepo.getWordListDirect(
                    wordListEssentialId.toInt())
            val wordListManhattanAdvancedFetched = wordRepo.getWordListDirect(
                    wordListAdvancedId.toInt())

            // Create default shuffled word lists
            var essentialShuffledSequence: ArrayList<Int> = arrayListOf()
            wordListManhattanEssentialFetched.forEach { essentialShuffledSequence.add(it.id) }
            val wordListManhattanEssentialShuffledUpdate = WordList(wordListEssentialId.toInt(),
                    wordListEssentialUUID, "", "Manhattan Essential - Shuffled",
                    essentialShuffledSequence[0], essentialShuffledSequence)

            var advancedShuffledSequence: ArrayList<Int> = arrayListOf()
            wordListManhattanAdvancedFetched.forEach { advancedShuffledSequence.add(it.id) }
            val wordListManhattanAdvancedShuffledUpdate = WordList(wordListAdvancedId.toInt(),
                    wordListAdvancedUUID, "", "Manhattan Advanced - Shuffled",
                    advancedShuffledSequence[0], advancedShuffledSequence)

            wordListRepo.updateWordList(wordListManhattanEssentialShuffledUpdate)
            wordListRepo.updateWordList(wordListManhattanAdvancedShuffledUpdate)

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