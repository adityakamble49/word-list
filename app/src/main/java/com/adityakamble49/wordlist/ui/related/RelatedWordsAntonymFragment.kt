package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordAntonym


/**
 * Related Word Antonym Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsAntonymFragment : RelatedWordsCommonFragment<RelatedWordAntonym>() {

    companion object {
        fun newInstance() = RelatedWordsAntonymFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordAntonym> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsAntonymViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsAntonymAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordAntonym) = relatedWord.word
}