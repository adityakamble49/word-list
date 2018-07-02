package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.interactor.common.SingleUseCase
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Create Word List - Use Case
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
class CreateWordList @Inject constructor(
        private val wordListRepository: WordListRepository,
        postExecutionThread: PostExecutionThread) :
        SingleUseCase<WordList, CreateWordList.Params>(postExecutionThread) {

    override fun buildSingleUseCase(param: Params?): Single<WordList> {
        if (param == null) throw IllegalArgumentException()
        val wordList = WordList(0, UUID.randomUUID().toString(), "", param.wordListName, 0)
        return wordListRepository.createWordList(wordList)
    }

    data class Params constructor(val wordListName: String) {
        companion object {
            fun forWordList(wordListName: String): Params {
                return Params(wordListName)
            }
        }
    }
}