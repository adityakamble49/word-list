package com.adityakamble49.wordlist.cache.test

import com.adityakamble49.wordlist.data.model.WordListEntity
import java.util.*

/**
 * Extensions
 *
 * @author Aditya Kamble
 * @since 10/7/2018
 */

/**
 * Sort Word List Entities as per item ids
 */
fun List<WordListEntity>.sort() {
    Collections.sort(this, { item1, item2 ->
        return@sort item1.id.compareTo(item2.id)
    })
}
