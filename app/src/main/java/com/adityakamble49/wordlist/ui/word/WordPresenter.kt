package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.interactor.*
import com.adityakamble49.wordlist.model.*
import com.adityakamble49.wordlist.utils.Constants.DictateModeSpeedValues
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
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
        private val getCurrentWords: GetCurrentWords,
        private val getCurrentWordList: GetCurrentWordList,
        private val saveLastWordIdForWordList: SaveLastWordIdForWordList,
        private val getDictateModeConfig: GetDictateModeConfig,
        private val submitNewWord: SubmitNewWord,
        private val submitEditedWord: SubmitEditedWord,
        private val fetchMnemonic: FetchMnemonic,
        private val fetchWordInfo: FetchWordInfo) :
        WordContract.Presenter {

    private lateinit var viewModel: WordViewModel
    private var currentWordActivityMode: Int = WordActivity.Companion.WordActivityMode.NORMAL.ordinal
    private var currentWordId = 0
    private var isWordListEmpty = false

    override fun setWordViewModel(currentWordViewModel: WordViewModel) {
        this.viewModel = currentWordViewModel
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
            viewModel.setupSingleWord(wordRepo.getWordById(currentWordId))
            observeSingleWord()
        }
    }

    override fun onStop() {
        fetchMnemonic.dispose()
        fetchWordInfo.dispose()
    }

    private fun getCurrentWordList() {
        getCurrentWordList.execute().subscribe(GetCurrentWordListSubscriber())
    }

    private fun getDictateConfig() {
        viewModel.dictateModeConfig = getDictateModeConfig.execute()
    }

    private inner class GetCurrentWordListSubscriber : io.reactivex.Observer<WordList> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onNext(t: WordList) {
            viewModel.currentWordList = t
            if (!isAddMode()) {
                loadWords()
            }
        }
    }

    private fun loadWords() {
        getCurrentWords.execute().subscribe(GetWordListSubscriber())
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
            viewModel.wordList = t
            loadWord(currentWordActivityMode, currentWordId)
        }
    }

    private fun loadWord(currentWordActivityMode: Int, wordId: Int) {
        when (currentWordActivityMode) {
            WordActivity.Companion.WordActivityMode.LEARN.ordinal -> updateWord(
                    getWordById(viewModel.currentWordList.lastWordId))
            WordActivity.Companion.WordActivityMode.PRACTICE.ordinal -> {
                updateWordListForPractice()
                updateWord(viewModel.currentWord)
            }
            else -> updateWord(getWordById(wordId))
        }
    }

    private fun updateWordListForPractice() {
        val practiceWordList = mutableListOf<Word>()
        lateinit var shuffledPracticeWordList: List<Word>
        if (viewModel.wordListPractice.isEmpty()) {
            val lastWordId = viewModel.currentWordList.lastWordId
            for (i in 0 until viewModel.wordList.size) {
                val singleWord = viewModel.wordList[i]
                practiceWordList.add(singleWord)
                if (singleWord.id == lastWordId) {
                    break
                }
            }
            shuffledPracticeWordList = practiceWordList.shuffled()
        } else {
            shuffledPracticeWordList = viewModel.wordListPractice
        }
        viewModel.wordList = shuffledPracticeWordList
        viewModel.wordListPractice = shuffledPracticeWordList
        viewModel.currentWord = viewModel.wordList[viewModel.currentWordPosition]
    }

    private fun getWordById(wordId: Int): Word {
        viewModel.wordList.forEachIndexed { index, word ->
            if (word.id == wordId) {
                viewModel.currentWord = word
                viewModel.currentWordPosition = index
                return viewModel.currentWord
            }
        }
        viewModel.currentWord = viewModel.wordList[0]
        return viewModel.currentWord
    }

    override fun onClickShowInfo() {
        if (isPracticeMode()) {
            view.showWordInfo(viewModel.currentWord.information,
                    viewModel.currentWord.mnemonic)
        }
    }

    override fun onNextWordAction() {
        if (viewModel.currentWordPosition < viewModel.wordList.size - 1) {
            viewModel.currentWordPosition++
        }
        viewModel.currentWord = viewModel.wordList[viewModel.currentWordPosition]
        updateWord(viewModel.currentWord)
        onWordChanged()
    }

    override fun onPreviousWordAction() {
        if (viewModel.currentWordPosition > 0) {
            viewModel.currentWordPosition--
        }
        viewModel.currentWord = viewModel.wordList[viewModel.currentWordPosition]
        updateWord(viewModel.currentWord)
        onWordChanged()
    }

    private fun onWordChanged() {
        startDictate()
        view.scrollToTop()
    }

    override fun onDictateModeAction() {
        when (viewModel.isDictateModeOn) {
            false -> {
                viewModel.isDictateModeOn = true
                updateDictateModeSpeed()
                view.updateFABDictateIcon(R.drawable.ic_stop)
                startDictate()
            }
            true -> {
                viewModel.isDictateModeOn = false
                stopDictate()
                view.updateFABDictateIcon(R.drawable.ic_play)
            }
        }
    }

    private fun updateDictateModeSpeed() {
        view.updateDictateModeSpeed(when (viewModel.dictateModeConfig.dictateModeSpeed) {
            DictateModeSpeed.SLOWER -> DictateModeSpeedValues.SLOWER
            DictateModeSpeed.SLOW -> DictateModeSpeedValues.SLOW
            DictateModeSpeed.NORMAL -> DictateModeSpeedValues.NORMAL
            DictateModeSpeed.FAST -> DictateModeSpeedValues.FAST
            DictateModeSpeed.FASTER -> DictateModeSpeedValues.FASTER
        })
    }

    private fun startDictate() {
        if (viewModel.isDictateModeOn) {
            val wordInfo = getWordForDictate()
            view.speakWord(wordInfo)
        }
    }

    private fun getWordForDictate(): String {
        return when (viewModel.dictateModeConfig.dictateModeType) {
            DictateModeType.WORD_COMPLETE_INFO -> "${viewModel.currentWord.name}. ${viewModel.currentWord.information}"
            DictateModeType.WORD_DEFINITION -> {
                "${viewModel.currentWord.name}. ${extractWordDefinition()}"
            }
            DictateModeType.WORD_ONLY -> viewModel.currentWord.name
        }.replace("\n\n", ".").replace("\n", " ")
    }

    private fun extractWordDefinition(): String {
        return viewModel.currentWord.information.split("Usage:")[0]
    }

    private fun stopDictate() {
        view.stopSpeaking()
    }

    private fun updateWord(word: Word) {
        view.updateWord(word, viewModel.currentWordPosition + 1,
                viewModel.wordList.size)
    }

    override fun onClickWordTTS() {
        view.speakWord(viewModel.currentWord.name)
    }

    override fun onClickWordMnemonicsSync(word: String) {
        if (!word.isEmpty()) {
            view.showMnemonicProgress(true)
            fetchMnemonic.execute(word, FetchMnemonicObserver())
        } else {
            view.showMessage("Word Empty")
        }
    }

    override fun onClickWordInformationSync(word: String) {
        if (!word.isEmpty()) {
            view.showWordInfoProgress(true)
            fetchWordInfo.execute(word, FetchWordInfoObserver())
        }
    }

    inner class FetchMnemonicObserver : DisposableSingleObserver<String>() {
        override fun onError(e: Throwable) {}
        override fun onSuccess(t: String) {
            view.showMnemonicProgress(false)
            view.updateMnemonics(t)
        }

    }

    inner class FetchWordInfoObserver : DisposableSingleObserver<List<DictionaryWord>>() {
        override fun onError(e: Throwable) {}
        override fun onSuccess(t: List<DictionaryWord>) {
            view.showWordInfoProgress(false)
            view.updateWordInfo(formatDictionaryWords(t))
        }
    }

    private fun formatDictionaryWords(dictionaryWordList: List<DictionaryWord>): String {
        val sb = StringBuilder()
        dictionaryWordList.forEachIndexed { index, dictionaryWord ->
            sb.append("${index + 1}. " +
                    "Definition: [${dictionaryWord.type}] - ${dictionaryWord.definition}\n " +
                    "Example: ${dictionaryWord.example}")
            if (index + 1 != dictionaryWordList.size) {
                sb.append("\n\n")
            }
        }
        return sb.toString()
    }

    override fun onTTSDone() {
        if (viewModel.isDictateModeOn) {
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
                submitNewWord.execute(submittedWord).subscribe(SubmitNewWordObserver())
            } else {
                submittedWord.id = viewModel.currentWord.id
                submittedWord.listId = viewModel.currentWord.listId
                submitEditedWord.execute(submittedWord).subscribe(SubmitEditedWordObserver())
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
            saveLastWordIdForWordList.execute(viewModel.currentWordList.id,
                    viewModel.currentWord.id)
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
        viewModel.singleWord.observe(view, android.arch.lifecycle.Observer<Word> {
            it?.let {
                viewModel.currentWord = it
                view.updateWordSingle(viewModel.currentWord)
            }
        })
    }
}