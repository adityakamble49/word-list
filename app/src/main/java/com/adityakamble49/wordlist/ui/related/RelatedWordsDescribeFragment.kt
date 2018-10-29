package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordDescribe


/**
 * Related Word Describe Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsDescribeFragment : RelatedWordsCommonFragment<RelatedWordDescribe>() {

    companion object {
        fun newInstance() = RelatedWordsDescribeFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordDescribe> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsDescribeViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsDescribeAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordDescribe) = relatedWord.word
}