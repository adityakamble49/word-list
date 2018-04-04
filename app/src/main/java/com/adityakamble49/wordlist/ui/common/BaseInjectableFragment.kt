package com.adityakamble49.wordlist.ui.common

import android.content.Context
import dagger.android.support.AndroidSupportInjection

/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
abstract class BaseInjectableFragment : BaseFragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}