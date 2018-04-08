package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.interactor.GetWordListUseCase
import com.adityakamble49.wordlist.model.Word
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Word View Pager Presenter
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class WordPresenter @Inject constructor(
        private val view: WordContract.View,
        private val wordRepo: WordRepo,
        private val getWordListUseCase: GetWordListUseCase) :
        WordContract.Presenter {

    lateinit var wordViewModel: WordViewModel

    override fun initialize() {
        loadWords()
    }

    override fun loadWord(wordId: Int) {
        view.updateWord(getWordById(wordId))
    }

    override fun loadWords() {
        getWordListUseCase.execute().subscribe(GetWordListSubscriber())
    }

    private inner class GetWordListSubscriber : io.reactivex.Observer<List<Word>> {

        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            wordViewModel.wordList = t
            view.initializeActivityMode()
        }
    }

    private fun getWordById(wordId: Int): Word {
        for (word in wordViewModel.wordList) {
            if (word.id == wordId) return word
        }
        return wordViewModel.wordList[0]
    }
}