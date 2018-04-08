package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
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

    private val wordList: LiveData<List<Word>>
    private val wordListType = MutableLiveData<Int>()

    init {
        wordListType.postValue(preferenceHelper.currentListType)
        wordList = Transformations.switchMap(wordListType, {
            return@switchMap wordRepo.getWordList(it)
        })
    }

    fun getWordList() = wordList

    fun updateCurrentWordListType(wordListType: Int) {
        this.wordListType.value = wordListType
    }
}