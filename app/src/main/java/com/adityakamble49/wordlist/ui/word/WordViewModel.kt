package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * Word View Model
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
class WordViewModel @Inject constructor(
        private val wordRepo: WordRepo) : ViewModel() {

    lateinit var word: LiveData<Word>

}