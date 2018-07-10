package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.interactor.common.FlowableUseCase
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Get Word Lists - Use Case
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class GetWordLists @Inject constructor(
        private val wordListRepository: WordListRepository,
        postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<WordList>, Nothing>(postExecutionThread) {

    override fun buildUseCaseFlowable(param: Nothing?): Flowable<List<WordList>> {
        return wordListRepository.getWordLists()
    }
}