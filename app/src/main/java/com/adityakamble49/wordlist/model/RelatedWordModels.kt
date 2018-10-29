package com.adityakamble49.wordlist.model

/**
 * Related Words Common
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
abstract class RelatedWordCommon {
    lateinit var word: String
    var score: Int = 0
}

/**
 * Related Words Basic
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
class RelatedWordBasic : RelatedWordCommon() {
    var tags: List<String> = mutableListOf()
}

/**
 * Related Words Antonym
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordAntonym : RelatedWordCommon()

/**
 * Related Words Describe
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
class RelatedWordDescribe : RelatedWordCommon()

/**
 * Related Words Adjective
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
class RelatedWordAdjective : RelatedWordCommon()

/**
 * Related Words Triggered
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
class RelatedWordTriggered : RelatedWordCommon()