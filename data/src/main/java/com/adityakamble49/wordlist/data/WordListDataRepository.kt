package com.adityakamble49.wordlist.data

import com.adityakamble49.wordlist.data.mapper.WordListMapper
import com.adityakamble49.wordlist.data.store.WordListDataStoreFactory
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * WordList Data Repository
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class WordListDataRepository @Inject constructor(
        private val mapper: WordListMapper,
        private val factory: WordListDataStoreFactory) : WordListRepository {

    override fun createWordList(wordList: WordList): Single<WordList> {
        return factory.getDataStore()
                .saveWordList(mapper.mapToEntity(wordList))
                .map { wordListId ->
                    WordList(wordListId.toInt(), wordList.hash,
                            wordList.marketplaceFilename, wordList.name,
                            wordList.lastWordId)
                }
    }

    override fun getWordLists(): Flowable<List<WordList>> {
        return factory.getDataStore().getWordLists()
                .map {
                    it.map { mapper.mapFromEntity(it) }
                }

    }
}