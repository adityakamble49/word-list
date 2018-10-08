package com.adityakamble49.wordlist.ui.related

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.adityakamble49.wordlist.R
import kotlinx.android.synthetic.main.activity_related_words.*

/**
 * Related Words Activity
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class RelatedWordsActivity : AppCompatActivity(), View.OnTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_words)

        bindView()
    }

    private fun bindView() {

        et_search_bar.setOnTouchListener(this)

        setupRelatedWordsPager()
    }

    private fun setupRelatedWordsPager() {
        val viewPagerAdapter = RelatedWordsViewPagerAdapter(supportFragmentManager)
        vp_related_words_container.isPagingEnabled = false
        vp_related_words_container.adapter = viewPagerAdapter
        tl_related_words_category.setupWithViewPager(vp_related_words_container)

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

    private inner class RelatedWordsViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {

        val relatedWordsTypes: Array<String> = resources.getStringArray(R.array.related_words_types)

        override fun getItem(position: Int): Fragment {
            return MeansLikeFragment.newInstance()
        }

        override fun getCount(): Int {
            return relatedWordsTypes.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return relatedWordsTypes[position]
        }
    }
}