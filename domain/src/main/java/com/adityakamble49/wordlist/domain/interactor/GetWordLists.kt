package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.interactor.common.ObservableUseCase
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import io.reactivex.Observable
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
        ObservableUseCase<List<WordList>, Nothing>(postExecutionThread) {

    override fun buildUseCaseObservable(param: Nothing?): Observable<List<WordList>> {
        return wordListRepository.getWordLists()
    }
}