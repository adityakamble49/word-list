package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.WordComparator
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListType
import com.adityakamble49.wordlist.utils.DataProcessor
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
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
class ImportWordListToDatabaseUseCase @Inject constructor(
        private val dataProcessor: DataProcessor,
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo,
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(importWordListToDBEmitter: CompletableEmitter) {
                // Insert Words into DB
                val wordListManhattanEssential = dataProcessor.parseWordList(
                        WordListType.MANHATTAN_ESSENTIAL)
                val wordListManhattanAdvanced = dataProcessor.parseWordList(
                        WordListType.MANHATTAN_ADVANCED)
                wordRepo.insertWords(wordListManhattanEssential)
                wordRepo.insertWords(wordListManhattanAdvanced)

                // Fetch inserted words with updated primary key
                val wordListManhattanEssentialFetched = wordRepo.getWordListDirect(
                        WordListType.MANHATTAN_ESSENTIAL.ordinal)
                val wordListManhattanAdvancedFetched = wordRepo.getWordListDirect(
                        WordListType.MANHATTAN_ADVANCED.ordinal)

                // Create default shuffled word lists
                var essentialShuffledSequence: ArrayList<Int> = arrayListOf()
                wordListManhattanEssentialFetched.forEach { essentialShuffledSequence.add(it.id) }
                val wordListManhattanEssentialShuffled = WordList(0,
                        "Manhattan Essential - Shuffled",
                        WordListType.MANHATTAN_ESSENTIAL.ordinal,
                        essentialShuffledSequence)

                var advancedShuffledSequence: ArrayList<Int> = arrayListOf()
                wordListManhattanAdvancedFetched.forEach { advancedShuffledSequence.add(it.id) }
                val wordListManhattanAdvancedShuffled = WordList(0,
                        "Manhattan Advanced - Shuffled",
                        WordListType.MANHATTAN_ADVANCED.ordinal,
                        advancedShuffledSequence)

                wordListRepo.insert(wordListManhattanEssentialShuffled)
                wordListRepo.insert(wordListManhattanAdvancedShuffled)

                // Sort Words alphabetically
                Collections.sort(wordListManhattanEssentialFetched, WordComparator())
                Collections.sort(wordListManhattanAdvancedFetched, WordComparator())

                // Create default shuffled word lists
                var essentialAlphabeticalSequence: ArrayList<Int> = arrayListOf()
                wordListManhattanEssentialFetched.forEach { essentialAlphabeticalSequence.add(it.id) }
                val wordListManhattanEssentialAlphabetical = WordList(0,
                        "Manhattan Essential - Alphabetical",
                        WordListType.MANHATTAN_ESSENTIAL.ordinal,
                        essentialAlphabeticalSequence)

                var advancedAlphabeticalSequence: ArrayList<Int> = arrayListOf()
                wordListManhattanAdvancedFetched.forEach { advancedAlphabeticalSequence.add(it.id) }
                val wordListManhattanAdvancedAlphabetical = WordList(0,
                        "Manhattan Advanced - Alphabetical",
                        WordListType.MANHATTAN_ADVANCED.ordinal,
                        advancedAlphabeticalSequence)

                wordListRepo.insert(wordListManhattanEssentialAlphabetical)
                wordListRepo.insert(wordListManhattanAdvancedAlphabetical)

                preferenceHelper.areWordsImported = true
                importWordListToDBEmitter.onComplete()
            }
        })
    }

    fun execute(): Completable {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}