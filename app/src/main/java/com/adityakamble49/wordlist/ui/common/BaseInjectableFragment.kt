package com.adityakamble49.wordlist.ui.common

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

/**
 * Base Injectable Fragment
 *
 * @author Aditya Kamble
 * @since 19/10/2018
 */
abstract class BaseInjectableFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}