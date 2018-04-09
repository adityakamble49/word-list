package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.interactor.GetWordListUseCase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.OnSwipeTouchListener
import com.adityakamble49.wordlist.utils.WordUtils
import io.reactivex.Observer
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
        private val getWordListUseCase: GetWordListUseCase,
        private val getCurrentWordListUseCase: GetCurrentWordListUseCase) :
        WordContract.Presenter {

    lateinit var wordViewModel: WordViewModel

    override fun initialize() {
        getCurrentWordList()
    }

    private fun getCurrentWordList() {
        getCurrentWordListUseCase.execute().subscribe(GetCurrentWordListSubscriber())
    }

    private inner class GetCurrentWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            wordViewModel.currentWordList = t
            loadWords()
        }
    }

    override fun loadWord(wordId: Int) {
        view.updateWord(getWordById(wordId))
    }

    override fun loadWords() {
        getWordListUseCase.execute().subscribe(GetWordListSubscriber())
    }

    private inner class GetWordListSubscriber : Observer<List<Word>> {

        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            wordViewModel.wordList = WordUtils.sortWordList(t,
                    wordViewModel.currentWordList.wordSequenceList)
            view.initializeActivityMode()
        }
    }

    private fun getWordById(wordId: Int): Word {
        wordViewModel.wordList.forEachIndexed { index, word ->
            if (word.id == wordId) {
                wordViewModel.currentWord = word
                wordViewModel.currentWordPosition = index
                return wordViewModel.currentWord
            }
        }
        wordViewModel.currentWord = wordViewModel.wordList[0]
        return wordViewModel.currentWord
    }

    override fun onClickWordInformation() {
        view.updateWordInformation(wordViewModel.currentWord.information)
    }

    override fun onClickWordMnemonic() {
        view.updateWordMnemonic(wordViewModel.currentWord.mnemonic)
    }

    override fun onSwipe(swipeDirection: OnSwipeTouchListener.SwipeDirection) {
        if (swipeDirection == OnSwipeTouchListener.SwipeDirection.LEFT &&
                wordViewModel.currentWordPosition < wordViewModel.wordList.size) {
            wordViewModel.currentWordPosition++
        } else if (swipeDirection == OnSwipeTouchListener.SwipeDirection.RIGHT && wordViewModel.currentWordPosition > 0) {
            wordViewModel.currentWordPosition--
        }
        wordViewModel.currentWord = wordViewModel.wordList[wordViewModel.currentWordPosition]
        view.updateWord(wordViewModel.currentWord)
    }
}