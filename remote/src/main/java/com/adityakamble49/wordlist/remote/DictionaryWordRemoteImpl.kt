package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.data.repository.DictionaryWordRemote
import com.adityakamble49.wordlist.remote.mapper.DictionaryWordListMapper
import com.adityakamble49.wordlist.remote.service.WordListService
import io.reactivex.Single
import javax.inject.Inject

/**
 * DictionaryWord Remote Implementation
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class DictionaryWordRemoteImpl @Inject constructor(
        private val wordListService: WordListService,
        private val mapper: DictionaryWordListMapper) : DictionaryWordRemote {

    override fun getDictionaryWord(word: String): Single<List<DictionaryWordEntity>> {
        return wordListService.getWordInformation(word)
                .map {
                    it.map { mapper.mapFromModel(it) }
                }
    }
}