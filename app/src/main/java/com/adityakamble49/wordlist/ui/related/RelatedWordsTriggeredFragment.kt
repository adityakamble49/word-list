package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProviders
import com.adityakamble49.wordlist.model.RelatedWordTriggered


/**
 * Related Word Triggered Fragment
 *
 * @author Aditya Kamble
 * @since 29/10/2018
 */
class RelatedWordsTriggeredFragment : RelatedWordsCommonFragment<RelatedWordTriggered>() {

    companion object {
        fun newInstance() = RelatedWordsTriggeredFragment()
    }

    override fun getRelatedWordCommonViewModel(): RelatedWordsCommonViewModel<RelatedWordTriggered> {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsTriggeredViewModel::class.java)
    }

    override fun getRelatedWordCommonAdapter() = RelatedWordsTriggeredAdapter()

    override fun getCurrentWordName(relatedWord: RelatedWordTriggered) = relatedWord.word
}