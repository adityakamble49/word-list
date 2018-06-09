package com.adityakamble49.wordlist.ui.search

import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.model.Word
import javax.inject.Inject

/**
 * Search View Model
 *
 * @author Aditya Kamble
 * @since 9/6/2018
 */
class SearchViewModel @Inject constructor() : ViewModel() {
    lateinit var allWordsList: List<Word>
}