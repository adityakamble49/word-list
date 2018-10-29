package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordAdjective


/**
 * Related Word Adjective Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAdjectiveFragment : RelatedWordsCommonFragment<RelatedWordAdjective>() {

    companion object {
        fun newInstance() = RelatedWordsAdjectiveFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordAdjective> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsAdjectiveViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsAdjectiveAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordAdjective) = relatedWord.word
}