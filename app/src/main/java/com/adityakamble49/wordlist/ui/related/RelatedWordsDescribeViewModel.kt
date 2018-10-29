package com.adityakamble49.wordlist.ui.related

import com.adityakamble49.wordlist.model.RelatedWordDescribe
import com.adityakamble49.wordlist.remote.RemoteUrls
import com.adityakamble49.wordlist.remote.WordListService
import javax.inject.Inject

/**
 * Related Words Describe ViewModel
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsDescribeViewModel @Inject constructor(
        private val wordListService: WordListService) :
        RelatedWordsCommonViewModel<RelatedWordDescribe>() {

    override fun performRelatedWordRequest(query: String) = wordListService.getRelatedWordDescribe(
            RemoteUrls.getRelatedWordsDescribeUrl(query))
}