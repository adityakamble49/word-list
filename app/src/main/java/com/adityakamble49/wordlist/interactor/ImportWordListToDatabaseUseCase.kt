package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListJoinRepo
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.WordComparator
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListJoin
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
        private val wordListRepo: WordListRepo,
        private val wordListJoinRepo: WordListJoinRepo) {

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


                // Create default alphabetical word lists
                val wordListManhattanEssentialSorted = WordList(0,
                        "Manhattan Essential - Alphabetical")
                val wordListManhattanAdvancedSorted = WordList(0,
                        "Manhattan Advanced - Alphabetical")
                val essentialListId = wordListRepo.insert(wordListManhattanEssentialSorted)
                val advancedListId = wordListRepo.insert(wordListManhattanAdvancedSorted)

                // Sort Words alphabetically
                Collections.sort(wordListManhattanEssentialFetched, WordComparator())
                Collections.sort(wordListManhattanAdvancedFetched, WordComparator())

                // Insert alphabetically sorted words to respective WordLists
                val wordListJoinEssentialList = mutableListOf<WordListJoin>()
                wordListManhattanEssentialFetched.forEachIndexed { index, word ->
                    wordListJoinEssentialList.add(
                            WordListJoin(word.id, essentialListId.toInt(), index))
                }
                wordListJoinRepo.insertList(wordListJoinEssentialList)

                val wordListJoinAdvancedList = mutableListOf<WordListJoin>()
                wordListManhattanAdvancedFetched.forEachIndexed { index, word ->
                    wordListJoinAdvancedList.add(
                            WordListJoin(word.id, advancedListId.toInt(), index))
                }
                wordListJoinRepo.insertList(wordListJoinAdvancedList)

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