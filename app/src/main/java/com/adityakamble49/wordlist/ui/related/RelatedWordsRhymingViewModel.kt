package com.adityakamble49.wordlist.ui.related

import com.adityakamble49.wordlist.model.RelatedWordRhyming
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import javax.inject.Inject

/**
 * Related Words Rhyming ViewModel
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsRhymingViewModel @Inject constructor(
        private val wordListService: WordListService) :
        RelatedWordsCommonViewModel<RelatedWordRhyming>() {

    override fun performRelatedWordRequest(query: String) = wordListService.getRelatedWordRhyming(
            RemoteUrls.getRelatedWordsRhymingUrl(query))
}