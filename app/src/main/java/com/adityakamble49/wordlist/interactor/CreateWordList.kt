package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.model.WordList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Create Word List Use Case
 *
 * @author Aditya Kamble
 * @since 10/6/2018
 */
class CreateWordList @Inject constructor(
        private val wordListRepo: WordListRepo) : BaseUseCase() {

    private fun buildUseCaseObservable(wordListName: String): Single<WordList> {
        return Single.create { e ->
            val savedWordLists = wordListRepo.getWordListsDirect()
            savedWordLists.forEach {
                if (it.name.toLowerCase() == wordListName.toLowerCase()) {
                    throw WordListNameExistException("$wordListName - Word List Name Already Exist")
                }
            }
            val newWordList = WordList(0, UUID.randomUUID().toString(), "", wordListName, 0)
            val newWordListId = wordListRepo.insert(newWordList)
            newWordList.id = newWordListId.toInt()
            e.onSuccess(newWordList)
        }
    }

    fun execute(wordListName: String, observer: DisposableSingleObserver<WordList>) {
        val observable = buildUseCaseObservable(wordListName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposables(observable.subscribeWith(observer))
    }
}