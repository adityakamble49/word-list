package com.adityakamble49.wordlist.model

/**
 * Related Words Basic
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
data class RelatedWordBasic(
        var word: String,
        var score: Int,
        var tags: List<String>
)

/**
 * Related Words Describe
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
data class RelatedWordDescribe(
        var word: String,
        var score: Int
)

/**
 * Related Words Adjective
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
data class RelatedWordAdjective(
        var word: String,
        var score: Int
)

/**
 * Related Words Triggered
 *
 * @author Aditya Kamble
 * @since 1/10/2018
 */
data class RelatedWordTriggered(
        var word: String,
        var score: Int
)