package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.WordListEntity
import io.reactivex.Single

interface WordListDataStore {

    fun saveWordList(wordListEntity: WordListEntity): Single<Long>
}