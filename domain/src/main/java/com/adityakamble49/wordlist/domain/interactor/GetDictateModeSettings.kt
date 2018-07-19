package com.adityakamble49.wordlist.domain.interactor

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import com.adityakamble49.wordlist.domain.interactor.common.SingleUseCase
import com.adityakamble49.wordlist.domain.model.DictateModeSettings
import com.adityakamble49.wordlist.domain.repository.DictateModeSettingsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Get DictateModeSettings - Use Case
 *
 * @author Aditya Kamble
 * @since 19/7/2018
 */
class GetDictateModeSettings @Inject constructor(
        private val dictateModeSettingsRepository: DictateModeSettingsRepository,
        postExecutionThread: PostExecutionThread) :
        SingleUseCase<DictateModeSettings, Nothing>(postExecutionThread) {

    override fun buildSingleUseCase(param: Nothing?): Single<DictateModeSettings> {
        return dictateModeSettingsRepository.getDictateModeSettings()
    }
}