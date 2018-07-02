package com.adityakamble49.wordlist.data.test

import java.util.*

/**
 * Data Factory
 * It provides sample data for testing
 *
 * @author Aditya Kamble
 * @since 2/7/2018
 */
object DataFactory {

    fun randomUUID() = UUID.randomUUID().toString()

    fun randomInteger() = Random().nextInt()

    fun randomLong() = Random().nextLong()
}