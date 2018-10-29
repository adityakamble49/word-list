package com.adityakamble49.wordlist.ui.related

import com.adityakamble49.wordlist.model.RelatedWordAdjective
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import javax.inject.Inject

/**
 * Related Words Adjective ViewModel
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAdjectiveViewModel @Inject constructor(
        private val wordListService: WordListService) :
        RelatedWordsCommonViewModel<RelatedWordAdjective>() {

    override fun performRelatedWordRequest(query: String) = wordListService.getRelatedWordAdjective(
            RemoteUrls.getRelatedWordsAdjectiveUrl(query))
}