package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.model.WordList
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Create WordList UseCase
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
class CreateWordListUseCase @Inject constructor(
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseObservable(wordListName: String): Observable<Long> {
        return Observable.create(object : ObservableOnSubscribe<Long> {

            override fun subscribe(e: ObservableEmitter<Long>) {
                val wordListToInsert = WordList(0, wordListName)
                val wordListId = wordListRepo.insert(wordListToInsert)
                e.onNext(wordListId)
                e.onComplete()
            }
        })
    }

    private fun buildUseCaseObservable(wordListNames: List<String>): Observable<List<Long>> {
        return Observable.create(object : ObservableOnSubscribe<List<Long>> {

            override fun subscribe(e: ObservableEmitter<List<Long>>) {
                val listOfWordList = mutableListOf<WordList>()
                wordListNames.forEach { name -> listOfWordList.add(WordList(0, name)) }
                val wordListIds = wordListRepo.insertList(listOfWordList)
                e.onNext(wordListIds)
                e.onComplete()
            }
        })
    }

    fun execute(wordListName: String): Observable<Long> {
        return buildUseCaseObservable(wordListName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    fun execute(wordListNames: List<String>): Observable<List<Long>> {
        return buildUseCaseObservable(wordListNames)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}