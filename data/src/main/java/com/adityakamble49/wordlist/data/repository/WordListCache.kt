package com.adityakamble49.wordlist.data.repository

import com.adityakamble49.wordlist.data.model.WordListEntity
import io.reactivex.Single

interface WordListCache {

    fun saveWordList(wordListEntity: WordListEntity): Single<Long>
}