package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * Word List ViewModel
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModel @Inject constructor() : ViewModel() {

    private var wordList: MutableLiveData<List<Word>> = MutableLiveData()

    fun getWordList() = wordList
}