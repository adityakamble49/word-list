package com.adityakamble49.wordlist.ui.related

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import kotlinx.android.synthetic.main.activity_related_words.*
import javax.inject.Inject

/**
 * Related Words Activity
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class RelatedWordsActivity : BaseInjectableActivity(), View.OnTouchListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: RelatedWordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_words)

        setupViewModel()
        bindView()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RelatedWordsViewModel::class.java)
    }

    private fun bindView() {
        et_search_bar.setOnTouchListener(this)
        et_search_bar.addTextChangedListener(SearchQueryListener())
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

    private inner class SearchQueryListener : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                viewModel.searchQuery.postValue(it.toString())
            }
        }
    }

    private inner class RelatedWordsViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {

        private val POS_RELATED_WORD_BASIC = 0
        private val POS_RELATED_WORD_ANTONYM = 1
        private val POS_RELATED_WORD_DESCRIBE = 2

        val relatedWordsTypes: Array<String> = resources.getStringArray(R.array.related_words_types)

        override fun getItem(position: Int): Fragment {
            return when (position) {
                POS_RELATED_WORD_BASIC -> RelatedWordsBasicFragment.newInstance()
                POS_RELATED_WORD_ANTONYM -> RelatedWordsAntonymFragment.newInstance()
                POS_RELATED_WORD_DESCRIBE -> RelatedWordsDescribeFragment.newInstance()
                else -> RelatedWordsBasicFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return relatedWordsTypes.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return relatedWordsTypes[position]
        }
    }
}