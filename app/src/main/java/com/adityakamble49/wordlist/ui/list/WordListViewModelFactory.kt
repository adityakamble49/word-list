package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}