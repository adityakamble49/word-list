package com.adityakamble49.wordlist.interactor

import android.net.Uri
import com.adityakamble49.wordlist.cache.FileStorageHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Import Word List Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class ImportWordList @Inject constructor(
        private val wordListRepo: WordListRepo,
        private val wordRepo: WordRepo,
        private val fileStorageHelper: FileStorageHelper) : BaseRxUseCase() {

    private fun buildUseCaseObservable(importFilePath: Uri): Completable {
        return Completable.create { e ->
            val wordListName = fileStorageHelper.getFileNameWithoutExtension(importFilePath)

            // Create List
            val savedWordLists = wordListRepo.getWordListsDirect()
            savedWordLists.forEach {
                if (it.name.toLowerCase() == wordListName.toLowerCase()) {
                    throw WordListNameExistException("$wordListName - Word List Name Already Exist")
                }
            }
            val newWordList = WordList(0, UUID.randomUUID().toString(), "", wordListName, 0)
            val newWordListId = wordListRepo.insert(newWordList)
            newWordList.id = newWordListId.toInt()

            // Add words
            val wordsJson = fileStorageHelper.readTextFromFile(importFilePath)
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Word>>() {}.type
            val words = gson.fromJson<List<Word>>(wordsJson, type)
            words.forEach {
                it.listId = newWordList.id
                it.id = 0
            }
            wordRepo.insertWords(words)

            e.onComplete()
        }
    }

    fun execute(importFilePath: Uri, observer: DisposableCompletableObserver) {
        val observable = buildUseCaseObservable(importFilePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}