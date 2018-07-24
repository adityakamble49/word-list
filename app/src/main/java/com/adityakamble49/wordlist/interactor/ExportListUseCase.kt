package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.FileStorageHelper
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * Export Word List Use Case
 *
 * @author Aditya Kamble
 * @since 24/7/2018
 */
class ExportListUseCase @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val fileStorageHelper: FileStorageHelper,
        private val wordListRepo: WordListRepo,
        private val wordRepo: WordRepo) : BaseRxUseCase() {

    private fun buildUseCaseObservable(): Completable {
        return Completable.create { e ->
            val currentWordListId = preferenceHelper.currentLoadedListId
            val wordListName = wordListRepo.getWordListById(currentWordListId).name
            val currentWords = wordRepo.getWordListDirect(currentWordListId)
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Word>>() {}.type
            val wordJson = gson.toJson(currentWords, type)
            val exportDir = File(Constants.Files.EXPORT_DIR)
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }
            fileStorageHelper.writeFileToExternalStorage(Constants.Files.EXPORT_DIR,
                    "$wordListName.json", wordJson)
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