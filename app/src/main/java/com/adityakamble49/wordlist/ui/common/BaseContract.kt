package com.adityakamble49.wordlist.ui.common

import android.arch.lifecycle.LifecycleOwner

/**
 * BaseContract
 *
 * @author Aditya Kamble
 * @since 7/4/2018
 */
interface BaseContract {

    interface View : LifecycleOwner
    interface Presenter {
        fun initialize()
        fun onStop()
    }
}