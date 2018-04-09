package com.adityakamble49.wordlist.model

/**
 * Word Comparator
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
class WordComparator : Comparator<Word> {
    override fun compare(o1: Word, o2: Word) = o1.name.compareTo(o2.name)
}