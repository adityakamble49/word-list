package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordBasic


/**
 * Related Word Basic Fragment
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class RelatedWordsBasicFragment : RelatedWordsCommonFragment<RelatedWordBasic>() {

    companion object {
        fun newInstance() = RelatedWordsBasicFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordBasic> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsBasicViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsBasicAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordBasic) = relatedWord.word
}