package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordRepo

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModelFactory(
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel(preferenceHelper, wordRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}