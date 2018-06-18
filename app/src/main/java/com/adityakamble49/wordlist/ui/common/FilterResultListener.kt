package com.adityakamble49.wordlist.ui.common

/**
 * Filter Result Listener
 *
 * @author Aditya Kamble
 * @since 18/6/2018
 */
interface FilterResultListener<in T> {
    fun onFilteredResult(list: List<T>)
}