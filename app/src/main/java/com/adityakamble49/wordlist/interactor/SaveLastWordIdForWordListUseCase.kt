package com.adityakamble49.wordlist.interactor

import com.adityakamble49.wordlist.cache.db.WordListRepo
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save LastWordId For WordList UseCase
 *
 * @author Aditya Kamble
 * @since 10/4/2018
 */
class SaveLastWordIdForWordListUseCase @Inject constructor(
        private val wordListRepo: WordListRepo) {

    private fun buildUseCaseObservable(wordListId: Int, lastWordId: Int): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(updateLastwordEmmiter: CompletableEmitter) {
                wordListRepo.updateLastWordIdForWordList(wordListId, lastWordId)
                updateLastwordEmmiter.onComplete()
            }
        })
    }

    fun execute(wordListId: Int, lastWordId: Int): Completable {
        return buildUseCaseObservable(wordListId, lastWordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}