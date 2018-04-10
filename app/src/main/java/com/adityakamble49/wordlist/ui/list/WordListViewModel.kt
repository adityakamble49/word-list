package com.adityakamble49.wordlist.ui.list

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import javax.inject.Inject

/**
 * Word List ViewModel
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModel @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo) : ViewModel() {

    private lateinit var wordList: LiveData<List<Word>>
    private val currentWordListLive = MutableLiveData<WordList>()
    lateinit var currentWordList: WordList

    fun initialize() {
        wordList = Transformations.switchMap(currentWordListLive,
                Function<WordList, LiveData<List<Word>>> {
                    return@Function wordRepo.getWordList(it.listType)
                })
    }

    fun getWordList() = wordList

    fun updateCurrentLoadedSavedList(selectedSavedWordList: WordList) {
        currentWordList = selectedSavedWordList
        currentWordListLive.postValue(selectedSavedWordList)
    }




}