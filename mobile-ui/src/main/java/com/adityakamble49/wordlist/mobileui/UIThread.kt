package com.adityakamble49.wordlist.mobileui

import com.adityakamble49.wordlist.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * UIThread - Post Execution Thread
 * Specify scheduler to return result after execution
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class UIThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}