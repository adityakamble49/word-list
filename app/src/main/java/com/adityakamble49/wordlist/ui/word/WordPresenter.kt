package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.interactor.*
import com.adityakamble49.wordlist.model.DictateModeSpeed
import com.adityakamble49.wordlist.model.DictateModeType
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.utils.Constants.DictateModeSpeedValues
import com.adityakamble49.wordlist.utils.WordUtils
import io.reactivex.CompletableObserver
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
        private val saveLastWordIdForWordListUseCase: SaveLastWordIdForWordListUseCase,
        private val getDictateModeConfigUseCase: GetDictateModeConfigUseCase,
        private val submitNewWordUseCase: SubmitNewWordUseCase,
        private val submitEditedWordUseCase: SubmitEditedWordUseCase) :
        WordContract.Presenter {

    private lateinit var currentWordViewModel: WordViewModel
    private var currentWordActivityMode: Int = WordActivity.Companion.WordActivityMode.NORMAL.ordinal
    private var currentWordId = 0
    private var isWordListEmpty = false

    override fun setWordViewModel(currentWordViewModel: WordViewModel) {
        this.currentWordViewModel = currentWordViewModel
    }

    override fun setActivityMode(wordActivityMode: Int) {
        this.currentWordActivityMode = wordActivityMode
    }

    private fun isPracticeMode() =
            currentWordActivityMode == WordActivity.Companion.WordActivityMode.PRACTICE.ordinal

    private fun isAddMode() =
            currentWordActivityMode == WordActivity.Companion.WordActivityMode.ADD.ordinal

    override fun setWordId(wordId: Int) {
        this.currentWordId = wordId
    }

    override fun initialize() {
        view.initializeActivityMode(currentWordActivityMode)
        if (currentWordActivityMode != WordActivity.Companion.WordActivityMode.SINGLE.ordinal) {
            getCurrentWordList()
            getDictateConfig()
        } else {
            currentWordViewModel.setupSingleWord(currentWordId)
            observeSingleWord()
        }
    }

    private fun getCurrentWordList() {
        getCurrentWordListUseCase.execute().subscribe(GetCurrentWordListSubscriber())
    }

    private fun getDictateConfig() {
        currentWordViewModel.dictateModeConfig = getDictateModeConfigUseCase.execute()
    }

    private inner class GetCurrentWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            currentWordViewModel.currentWordList = t
            if (!isAddMode()) {
                loadWords()
            }
        }
    }

    private fun loadWords() {
        getCurrentWordsUseCase.execute().subscribe(GetWordListSubscriber())
    }

    private inner class GetWordListSubscriber : io.reactivex.Observer<List<Word>> {

        override fun onSubscribe(d: Disposable) {}
        override fun onComplete() {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: List<Word>) {
            if (t.isEmpty()) {
                view.showEmptyListWarning()
                isWordListEmpty = true
                return
            }
            currentWordViewModel.wordList = WordUtils.sortWords(t,
                    currentWordViewModel.currentWordList.wordSequenceList)
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

    override fun onClickShowInfo() {
        if (isPracticeMode()) {
            view.showWordInfo(currentWordViewModel.currentWord.information,
                    currentWordViewModel.currentWord.mnemonic)
        }
    }

    override fun onNextWordAction() {
        if (currentWordViewModel.currentWordPosition < currentWordViewModel.wordList.size - 1) {
            currentWordViewModel.currentWordPosition++
        }
        currentWordViewModel.currentWord = currentWordViewModel.wordList[currentWordViewModel.currentWordPosition]
        updateWord(currentWordViewModel.currentWord)
        onWordChanged()
    }

    override fun onPreviousWordAction() {
        if (currentWordViewModel.currentWordPosition > 0) {
            currentWordViewModel.currentWordPosition--
        }
        currentWordViewModel.currentWord = currentWordViewModel.wordList[currentWordViewModel.currentWordPosition]
        updateWord(currentWordViewModel.currentWord)
        onWordChanged()
    }

    private fun onWordChanged() {
        startDictate()
    }

    override fun onDictateModeAction() {
        when (currentWordViewModel.isDictateModeOn) {
            false -> {
                currentWordViewModel.isDictateModeOn = true
                updateDictateModeSpeed()
                view.updateFABDictateIcon(R.drawable.ic_stop)
                startDictate()
            }
            true -> {
                currentWordViewModel.isDictateModeOn = false
                stopDictate()
                view.updateFABDictateIcon(R.drawable.ic_play)
            }
        }
    }

    private fun updateDictateModeSpeed() {
        view.updateDictateModeSpeed(when (currentWordViewModel.dictateModeConfig.dictateModeSpeed) {
            DictateModeSpeed.SLOWER -> DictateModeSpeedValues.SLOWER
            DictateModeSpeed.SLOW -> DictateModeSpeedValues.SLOW
            DictateModeSpeed.NORMAL -> DictateModeSpeedValues.NORMAL
            DictateModeSpeed.FAST -> DictateModeSpeedValues.FAST
            DictateModeSpeed.FASTER -> DictateModeSpeedValues.FASTER
        })
    }

    private fun startDictate() {
        if (currentWordViewModel.isDictateModeOn) {
            val wordInfo = getWordForDictate()
            view.speakWord(wordInfo)
        }
    }

    private fun getWordForDictate(): String {
        return when (currentWordViewModel.dictateModeConfig.dictateModeType) {
            DictateModeType.WORD_COMPLETE_INFO -> "${currentWordViewModel.currentWord.name}. ${currentWordViewModel.currentWord.information}"
            DictateModeType.WORD_DEFINITION -> {
                "${currentWordViewModel.currentWord.name}. ${extractWordDefinition()}"
            }
            DictateModeType.WORD_ONLY -> currentWordViewModel.currentWord.name
        }.replace("\n\n", ".").replace("\n", " ")
    }

    private fun extractWordDefinition(): String {
        return currentWordViewModel.currentWord.information.split("Usage:")[0]
    }

    private fun stopDictate() {
        view.stopSpeaking()
    }

    private fun updateWord(word: Word) {
        view.updateWord(word, currentWordViewModel.currentWordPosition + 1,
                currentWordViewModel.wordList.size)
    }

    override fun onClickWordTTS() {
        view.speakWord(currentWordViewModel.currentWord.name)
    }

    override fun onTTSDone() {
        if (currentWordViewModel.isDictateModeOn) {
            onNextWordAction()
        }
    }

    override fun onPause() {
        updateLastWordInWordList()
    }

    override fun onClickEditWord() {
        view.toggleEditMode(true)
    }

    override fun onClickSubmitWord() {
        view.submitWord()
    }

    override fun submitEditedWord(submittedWord: Word) {
        if (isWordValid(submittedWord)) {
            if (isAddMode()) {
                submitNewWordUseCase.execute(submittedWord).subscribe(SubmitNewWordObserver())
            } else {
                submittedWord.id = currentWordViewModel.currentWord.id
                submittedWord.listId = currentWordViewModel.currentWord.listId
                submitEditedWordUseCase.execute(submittedWord).subscribe(SubmitEditedWordObserver())
            }
        } else {
            view.submitWordInvalid()
        }
    }

    private fun isWordValid(submittedWord: Word): Boolean {
        if (!submittedWord.name.isEmpty()) {
            return true
        }
        return false
    }

    private inner class SubmitNewWordObserver : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onComplete() {
            view.addWordSuccess()
        }
    }

    private inner class SubmitEditedWordObserver : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onComplete() {
            view.editWordSuccess()
        }
    }

    private fun updateLastWordInWordList() {
        if (isWordListEmpty) {
            return
        }
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

    private fun observeSingleWord() {
        currentWordViewModel.singleWord.observe(view, android.arch.lifecycle.Observer<Word> {
            it?.let {
                currentWordViewModel.currentWord = it
                view.updateWordSingle(currentWordViewModel.currentWord)
            }
        })
    }
}