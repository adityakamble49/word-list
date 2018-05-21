package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.interactor.GetCurrentWordListUseCase
import com.adityakamble49.wordlist.interactor.GetCurrentWordsUseCase
import com.adityakamble49.wordlist.interactor.SaveLastWordIdForWordListUseCase
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
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
        private val getCurrentWordsUseCase: GetCurrentWordsUseCase,
        private val getCurrentWordListUseCase: GetCurrentWordListUseCase,
        private val saveLastWordIdForWordListUseCase: SaveLastWordIdForWordListUseCase) :
        WordContract.Presenter {

    private lateinit var currentWordViewModel: WordViewModel
    private var currentWordActivityMode: Int = WordActivity.Companion.WordActivityMode.NORMAL.ordinal
    private var currentWordId = 0
    private var isDictationMode = false

    override fun setWordViewModel(currentWordViewModel: WordViewModel) {
        this.currentWordViewModel = currentWordViewModel
    }

    override fun setActivityMode(wordActivityMode: Int) {
        this.currentWordActivityMode = wordActivityMode
    }

    override fun setWordId(wordId: Int) {
        this.currentWordId = wordId
    }

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
            currentWordViewModel.currentWordList = t
            loadWords()
        }
    }

    private fun loadWords() {
        getCurrentWordsUseCase.execute().subscribe(GetWordListSubscriber())
    }

    private inner class GetWordListSubscriber : Observer<List<Word>> {

        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            currentWordViewModel.wordList = WordUtils.sortWords(t,
                    currentWordViewModel.currentWordList.wordSequenceList)
            view.initializeActivityMode(currentWordActivityMode)
            loadWord(currentWordActivityMode, currentWordId)
        }
    }

    private fun loadWord(currentWordActivityMode: Int, wordId: Int) {
        when (currentWordActivityMode) {
            WordActivity.Companion.WordActivityMode.LEARN.ordinal -> updateWord(
                    getWordById(currentWordViewModel.currentWordList.lastWordId))
            WordActivity.Companion.WordActivityMode.PRACTICE.ordinal -> {
                updateWordListForPractice()
                updateWord(currentWordViewModel.currentWord)
            }
            else -> updateWord(getWordById(wordId))
        }
    }

    private fun updateWordListForPractice() {
        val practiceWordList = mutableListOf<Word>()
        lateinit var shuffledPracticeWordList: List<Word>
        if (currentWordViewModel.wordListPractice.isEmpty()) {
            val lastWordId = currentWordViewModel.currentWordList.lastWordId
            for (i in 0 until currentWordViewModel.wordList.size) {
                val singleWord = currentWordViewModel.wordList[i]
                practiceWordList.add(singleWord)
                if (singleWord.id == lastWordId) {
                    break
                }
            }
            shuffledPracticeWordList = practiceWordList.shuffled()
        } else {
            shuffledPracticeWordList = currentWordViewModel.wordListPractice
        }
        currentWordViewModel.wordList = shuffledPracticeWordList
        currentWordViewModel.wordListPractice = shuffledPracticeWordList
        currentWordViewModel.currentWord = currentWordViewModel.wordList[currentWordViewModel.currentWordPosition]
    }

    private fun getWordById(wordId: Int): Word {
        currentWordViewModel.wordList.forEachIndexed { index, word ->
            if (word.id == wordId) {
                currentWordViewModel.currentWord = word
                currentWordViewModel.currentWordPosition = index
                return currentWordViewModel.currentWord
            }
        }
        currentWordViewModel.currentWord = currentWordViewModel.wordList[0]
        return currentWordViewModel.currentWord
    }

    override fun onClickWordInformation() {
        view.updateWordInformation(currentWordViewModel.currentWord.information)
    }

    override fun onClickWordMnemonic() {
        view.updateWordMnemonic(currentWordViewModel.currentWord.mnemonic)
    }

    override fun onNextWordAction() {
        if (currentWordViewModel.currentWordPosition < currentWordViewModel.wordList.size - 1) {
            currentWordViewModel.currentWordPosition++
        }
        currentWordViewModel.currentWord = currentWordViewModel.wordList[currentWordViewModel.currentWordPosition]
        updateWord(currentWordViewModel.currentWord)
    }

    override fun onPreviousWordAction() {
        if (currentWordViewModel.currentWordPosition > 0) {
            currentWordViewModel.currentWordPosition--
        }
        currentWordViewModel.currentWord = currentWordViewModel.wordList[currentWordViewModel.currentWordPosition]
        updateWord(currentWordViewModel.currentWord)
    }

    override fun onDictationModeAction() {
        isDictationMode = true
        val wordInfo = "${currentWordViewModel.currentWord.name} ${currentWordViewModel.currentWord.information}"
        view.speakWord(wordInfo)
    }

    private fun updateWord(word: Word) {
        view.updateWord(word, currentWordViewModel.currentWordPosition + 1,
                currentWordViewModel.wordList.size)
    }

    override fun onClickWordTTS() {
        view.speakWord(currentWordViewModel.currentWord.name)
    }

    override fun onTTSDone() {
        if (isDictationMode) {
            onNextWordAction()
            onDictationModeAction()
        }
    }

    override fun onPause() {
        updateLastWordInWordList()
    }

    private fun updateLastWordInWordList() {
        if (currentWordActivityMode == WordActivity.Companion.WordActivityMode.LEARN.ordinal) {
            saveLastWordIdForWordListUseCase.execute(currentWordViewModel.currentWordList.id,
                    currentWordViewModel.currentWord.id)
                    .subscribe(SaveLastWordIdForWordListSubscriber())
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