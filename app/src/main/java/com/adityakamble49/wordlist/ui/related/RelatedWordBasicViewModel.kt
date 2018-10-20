package com.adityakamble49.wordlist.ui.related

import com.adityakamble49.wordlist.model.RelatedWordBasic
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import javax.inject.Inject

/**
 * Means Like View Model
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
class RelatedWordBasicViewModel @Inject constructor(
        private val wordListService: WordListService) :
        RelatedWordCommonViewModel<RelatedWordBasic>() {

    override fun performRelatedWordRequest(query: String) = wordListService.getRelatedWordBasic(
            RemoteUrls.getRelatedWordsBasicUrl(query))
}