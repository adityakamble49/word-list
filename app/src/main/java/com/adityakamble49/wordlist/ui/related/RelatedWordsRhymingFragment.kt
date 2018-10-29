package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordRhyming


/**
 * Related Word Rhyming Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsRhymingFragment : RelatedWordsCommonFragment<RelatedWordRhyming>() {

    companion object {
        fun newInstance() = RelatedWordsRhymingFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordRhyming> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsRhymingViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsRhymingAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordRhyming) = relatedWord.word
}