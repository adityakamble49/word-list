package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.wordlist.cache.PreferenceHelper

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class MainActivityViewModelFactory(private val preferenceHelper: PreferenceHelper) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(preferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}