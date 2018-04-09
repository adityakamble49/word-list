package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListRepo
import com.adityakamble49.wordlist.model.WordList
import javax.inject.Inject

/**
 * Main Activity View Model
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class MainActivityViewModel @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordListRepo: WordListRepo) :
        ViewModel() {

    val savedWordList: LiveData<List<WordList>> = wordListRepo.getWordLists()
    val currentLoadedSavedList = MutableLiveData<WordList>()
    val currentListType = MutableLiveData<Int>()

    fun updateCurrentListType(wordListType: Int) {
        preferenceHelper.currentListType = wordListType
        currentListType.postValue(wordListType)
    }


}