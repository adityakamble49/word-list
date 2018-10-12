package com.adityakamble49.wordlist.ui.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.adityakamble49.wordlist.R
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Search Activity
 *
 * @author Aditya Kamble
 * @since 12/10/2018
 */
class SearchActivity : AppCompatActivity(), View.OnTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        bindView()
    }

    private fun bindView() {
        handleSearchBarEvents()
    }


    private fun handleSearchBarEvents() {
        et_search_bar.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val DRAWABLE_START = 0
        val DRAWABLE_END = 2

        if (event?.action == MotionEvent.ACTION_UP) {
            if (event.rawX > (et_search_bar.right - et_search_bar.compoundDrawables[DRAWABLE_END].bounds.width() - et_search_bar.paddingEnd)) {
                return true
            } else if (event.rawX < (et_search_bar.left + et_search_bar.compoundDrawables[DRAWABLE_START].bounds.width() + et_search_bar.paddingStart)) {
                finish()
                return true
            }
        }

        return false
    }
}