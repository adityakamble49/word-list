package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.WordEntity
import com.adityakamble49.wordlist.data.repository.WordRemote
import com.adityakamble49.wordlist.remote.mapper.WordMapper
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.utils.Constants.RemoteUrls
import io.reactivex.Single
import javax.inject.Inject

/**
 * Word Remote Implementation
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class WordRemoteImpl @Inject constructor(
        private val wordListService: WordListService,
        private val mapper: WordMapper) : WordRemote {

    override fun getWords(wordListUrl: String): Single<List<WordEntity>> {
        return wordListService.getWordsFromMarketplace(wordListUrl,
                "token ${RemoteUrls.GITHUB_AUTH_TOKEN}")
                .map {
                    it.map { mapper.mapFromModel(it) }
                }
    }
}