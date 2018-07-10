package com.adityakamble49.wordlist.presentation.state

/**
 * Resource
 * To hold state and data of any resource needed for UI
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class Resource<out T> constructor(
        val state: ResourceState,
        val data: T?,
        val message: String?)