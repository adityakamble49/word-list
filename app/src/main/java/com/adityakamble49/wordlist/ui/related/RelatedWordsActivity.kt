package com.adityakamble49.wordlist.ui.related

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.adityakamble49.wordlist.R
import kotlinx.android.synthetic.main.activity_related_words.*

/**
 * Related Words Activity
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class RelatedWordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_words)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        vp_related_words_container.adapter = viewPagerAdapter
        tl_related_words_category.setupWithViewPager(vp_related_words_container)
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return MeansLikeFragment.newInstance()
        }

        override fun getCount(): Int {
            return 8
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Similar"
        }
    }
}