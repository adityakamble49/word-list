package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * Word View Pager Presenter
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class WordPresenter @Inject constructor(
        private val view: WordContract.View,
        private val wordRepo: WordRepo) :
        WordContract.Presenter {
    lateinit var wordViewModel: WordViewModel

    private var wordId = MutableLiveData<Int>()
    private lateinit var word: LiveData<Word>

    override fun initialize() {
        word = Transformations.switchMap(
                wordId) { input -> wordRepo.getWordById(input) }
        observeWord()
    }

    fun updateWordId(id: Int) {
        wordId.postValue(id)
    }

    private fun observeWord() {
        word.observe(view, Observer<Word> { t ->
            t?.let {
                view.updateWord(it)
            }
        })
    }

}