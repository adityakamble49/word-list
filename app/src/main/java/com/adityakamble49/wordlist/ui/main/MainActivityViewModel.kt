package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.PreferenceHelper
import javax.inject.Inject

/**
 * Main Activity View Model
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class MainActivityViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) :
        ViewModel() {

    fun changeCurrentListType(currentListType: Int) {
        preferenceHelper.currentListType = currentListType
    }

    fun getCurrentListType() = preferenceHelper.currentListType
}