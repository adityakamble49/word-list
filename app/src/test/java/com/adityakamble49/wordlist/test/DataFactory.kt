package com.adityakamble49.wordlist.test

import java.util.*

/**
 * Data Factory
 * It provides sample data for testing
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object DataFactory {

    private val random = Random()

    fun randomString() = UUID.randomUUID().toString()

    fun randomInteger() = Random().nextInt()

    fun randomLong() = Random().nextLong()

    fun makeRandom(bound: Int) = random.nextInt(bound)
}