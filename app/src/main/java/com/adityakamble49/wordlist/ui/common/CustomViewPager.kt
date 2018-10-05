package com.adityakamble49.wordlist.ui.common

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Custom View Pager
 *
 * @author Aditya Kamble
 * @since 5/10/2018
 */
class CustomViewPager : ViewPager {

    var isPagingEnabled = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isPagingEnabled && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isPagingEnabled && return super.onInterceptTouchEvent(ev)
    }
}