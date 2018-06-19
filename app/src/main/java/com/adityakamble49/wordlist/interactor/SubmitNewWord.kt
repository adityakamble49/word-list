package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
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
        private val wordRepo: WordRepo) {

    private fun buildUseCaseObservable(newWord: Word): Completable {
        return Completable.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val currentWordList = wordListRepo.getWordListById(currentWordListId)
            newWord.listId = currentWordList.id
            val newWordId = wordRepo.insertWord(newWord)
            currentWordList.wordSequenceList.add(newWordId.toInt())
            wordListRepo.updateWordList(currentWordList)
            e.onComplete()
        }
    }

    fun execute(newWord: Word): Completable {
        return buildUseCaseObservable(newWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}