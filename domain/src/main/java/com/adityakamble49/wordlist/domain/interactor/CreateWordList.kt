package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.exceptions.WordListNameExistException
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

    override fun buildUseCase(param: Params?): Single<WordList> {
        if (param == null) throw IllegalArgumentException()
        val savedWordLists = wordListRepository.getWordLists()
        savedWordLists.forEach {
            if (it.name.toLowerCase() == param.wordListName.toLowerCase()) {
                throw WordListNameExistException(
                        "${param.wordListName} - Word List Name Already Exist")
            }
        }
        val newWordList = WordList(0, UUID.randomUUID().toString(), "", param.wordListName, 0)
        val newWordListId = wordListRepository.insertWordList(newWordList)
        newWordList.id = newWordListId.toInt()
        return Single.just(newWordList)
    }

    data class Params constructor(val wordListName: String) {
        companion object {
            fun forWordList(wordListName: String): Params {
                return Params(wordListName)
            }
        }
    }
}