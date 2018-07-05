package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.WordEntity
import io.reactivex.Single

/**
 * Word Remote Interface
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
interface WordRemote {

    fun getWords(wordListUrl: String): Single<List<WordEntity>>
}