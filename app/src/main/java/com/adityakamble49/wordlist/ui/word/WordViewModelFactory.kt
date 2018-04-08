package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.cache.db.WordRepo

/**
 * WordViewModelFactory
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class WordViewModelFactory(private val wordRepo: WordRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(wordRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}