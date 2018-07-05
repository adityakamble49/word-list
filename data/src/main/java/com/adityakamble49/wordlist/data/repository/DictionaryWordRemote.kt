package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import io.reactivex.Single

/**
 * Dictionary Word Remote Interface
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
interface DictionaryWordRemote {

    fun getDictionaryWord(word: String): Single<List<DictionaryWordEntity>>
}