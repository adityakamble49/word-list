package com.adityakamble49.wordlist.ui.related

import com.adityakamble49.wordlist.model.RelatedWordAntonym
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import javax.inject.Inject

/**
 * Related Words Antonym ViewModel
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAntonymViewModel @Inject constructor(
        private val wordListService: WordListService) :
        RelatedWordsCommonViewModel<RelatedWordAntonym>() {

    override fun performRelatedWordRequest(query: String) = wordListService.getRelatedWordAntonym(
            RemoteUrls.getRelatedWordsAntonymUrl(query))
}