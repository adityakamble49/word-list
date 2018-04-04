package com.adityakamble49.wordlist.ui.common

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Base Fragment
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflateLayout(inflater, container)
        bindView(rootView)
        initializePresenter()
        return rootView
    }

    protected open fun inflateLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        @LayoutRes val layoutResource = getLayoutId()
        return inflater.inflate(layoutResource, container, false)
    }

    abstract fun getLayoutId(): Int

    abstract fun bindView(rootView: View)

    abstract fun initializePresenter()
}