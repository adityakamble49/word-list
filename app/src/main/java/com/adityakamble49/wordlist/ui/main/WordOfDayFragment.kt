package com.adityakamble49.wordlist.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.word.WordActivity
import kotlinx.android.synthetic.main.layout_card_word_of_day.*

/**
 * Word of Day Fragment
 *
 * @author Aditya Kamble
 * @since 5/10/2018
 */
class WordOfDayFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_card_word_of_day, container, false)
        bindView(view)
        return view
    }

    companion object {
        fun newInstance() = WordOfDayFragment()
    }

    private fun bindView(view: View) {
        view.setOnClickListener(this)
        with(view) {

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cv_wod_base_layout -> {
                val wordIntent = Intent(activity, WordActivity::class.java)
                val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity as Activity, tv_wod_word as View, "word_name")
                startActivity(wordIntent, activityOptionsCompat.toBundle())
            }
        }
    }
}