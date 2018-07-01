package com.adityakamble49.wordlist.domain.executor

import io.reactivex.Scheduler

/**
 * Post Execution Thread
 * Specify scheduler to return result after execution
 *
 * @author Aditya Kamble
 * @since 1/7/2018
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}