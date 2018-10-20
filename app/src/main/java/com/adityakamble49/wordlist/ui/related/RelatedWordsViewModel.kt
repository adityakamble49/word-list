package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Related Words View Model
 *
 * @author Aditya Kamble
 * @since 20/10/2018
 */
class RelatedWordsViewModel @Inject constructor() : ViewModel() {

    val searchQuery: MutableLiveData<String> = MutableLiveData()
}