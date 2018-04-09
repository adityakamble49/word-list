package com.adityakamble49.wordlist.model

/**
 * Word List Transformation
 *
 * @author Aditya Kamble
 * @since 9/4/2018
 */
data class WordListTransformation(
        var wordListTransformationType: WordListTransformationType,
        var wordListType: Int? = 0,
        var wordListSaved: WordList?
)

enum class WordListTransformationType {
    WORD_LIST_TYPE, SAVED_WORD_LIST
}