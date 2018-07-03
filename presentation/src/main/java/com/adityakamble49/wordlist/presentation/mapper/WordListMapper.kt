package com.adityakamble49.wordlist.presentation.mapper

import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.presentation.model.WordListView
import javax.inject.Inject

/**
 * Word List Mapper
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class WordListMapper @Inject constructor() : Mapper<WordList, WordListView> {

    override fun mapToView(model: WordList): WordListView {
        return WordListView(model.id, model.hash, model.marketplaceFilename, model.name,
                model.lastWordId)
    }
}