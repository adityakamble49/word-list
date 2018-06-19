package com.adityakamble49.wordlist.utils

import org.mockito.Mockito

/**
 * Mockito Extension
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)