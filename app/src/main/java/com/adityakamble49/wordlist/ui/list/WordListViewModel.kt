package com.adityakamble49.wordlist.ui.list

import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.db.WordRepo
import javax.inject.Inject

/**
 * Word List ViewModel
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModel @Inject constructor(
        private val wordRepo: WordRepo) : ViewModel() {

    fun getWordList() = wordRepo.getWordList()
}