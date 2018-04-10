package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.interactor.GetWordListUseCase
import com.adityakamble49.wordlist.interactor.SaveLastWordIdForWordListUseCase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.ui.common.OnSwipeTouchListener
import com.adityakamble49.wordlist.utils.WordUtils
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber
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
        private val getCurrentWordListUseCase: GetCurrentWordListUseCase,
        private val saveLastWordIdForWordListUseCase: SaveLastWordIdForWordListUseCase) :
        WordContract.Presenter {

    lateinit var wordViewModel: WordViewModel
    var currentWordActivityMode: Int = WordActivity.Companion.WordActivityMode.NORMAL.ordinal
    var currentWordId = 0

    override fun initialize() {
        getCurrentWordList()
    }

    private fun getCurrentWordList() {
        getCurrentWordListUseCase.execute().subscribe(GetCurrentWordListSubscriber())
    }

    private inner class GetCurrentWordListSubscriber : Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            wordViewModel.currentWordList = t
            loadWords()
        }
    }

    private fun loadWords() {
        getWordListUseCase.execute().subscribe(GetWordListSubscriber())
    }

    private inner class GetWordListSubscriber : Observer<List<Word>> {

        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            wordViewModel.wordList = WordUtils.sortWords(t,
                    wordViewModel.currentWordList.wordSequenceList)
            view.initializeActivityMode(currentWordActivityMode)
            loadWord(currentWordActivityMode, currentWordId)
        }
    }

    private fun loadWord(currentWordActivityMode: Int, wordId: Int) {
        when (currentWordActivityMode) {
            WordActivity.Companion.WordActivityMode.LEARN.ordinal -> view.updateWord(
                    getWordById(wordViewModel.currentWordList.lastWordId))
            WordActivity.Companion.WordActivityMode.PRACTICE.ordinal -> {
                updateWordListForPractice()
                view.updateWord(wordViewModel.wordList[wordViewModel.currentWordPosition])
            }
            else -> view.updateWord(getWordById(wordId))
        }
    }

    private fun updateWordListForPractice() {
        val practiceWordList = mutableListOf<Word>()
        lateinit var shuffledPracticeWordList: List<Word>
        if (wordViewModel.wordListPractice.isEmpty()) {
            val lastWordId = wordViewModel.currentWordList.lastWordId
            for (i in 0 until wordViewModel.wordList.size) {
                val singleWord = wordViewModel.wordList[i]
                practiceWordList.add(singleWord)
                if (singleWord.id == lastWordId) {
                    break
                }
            }
            shuffledPracticeWordList = practiceWordList.shuffled()
        } else {
            shuffledPracticeWordList = wordViewModel.wordListPractice
        }
        wordViewModel.wordList = shuffledPracticeWordList
        wordViewModel.wordListPractice = shuffledPracticeWordList
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

    override fun onPause() {
        updateLastWordInWordList()
    }

    private fun updateLastWordInWordList() {
        if (currentWordActivityMode == WordActivity.Companion.WordActivityMode.LEARN.ordinal) {
            saveLastWordIdForWordListUseCase.execute(wordViewModel.currentWordList.id,
                    wordViewModel.currentWord.id).subscribe(SaveLastWordIdForWordListSubscriber())
        }
    }

    private inner class SaveLastWordIdForWordListSubscriber : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onComplete() {
            Timber.i("Last word id saved")
        }

    }
}