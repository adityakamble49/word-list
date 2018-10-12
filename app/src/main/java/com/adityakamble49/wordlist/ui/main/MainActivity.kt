package com.adityakamble49.wordlist.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.WordList
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.common.ItemOffsetDecoration
import com.adityakamble49.wordlist.ui.marketplace.MarketplaceActivity
import com.adityakamble49.wordlist.ui.practice.PracticeActivity
import com.adityakamble49.wordlist.ui.related.RelatedWordsActivity
import com.adityakamble49.wordlist.ui.search.SearchActivity
import com.adityakamble49.wordlist.ui.settings.SettingsActivity
import com.adityakamble49.wordlist.ui.wordlist.WordListActivity
import com.adityakamble49.wordlist.utils.dpToPx
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Main Activity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class MainActivity : BaseInjectableActivity(), View.OnClickListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainActivityViewModel

    private lateinit var wordListAdapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
    }

    private fun bindView() {
        setupSearchBar()
        setupDashboardActions()
        setupDashboardViewPager()
        setupWordList()
    }

    private fun setupSearchBar() {
        et_search_bar.setOnClickListener(this)
    }

    private fun setupDashboardActions() {
        fab_related_words.setOnClickListener(this)
        fab_practice.setOnClickListener(this)
        fab_marketplace.setOnClickListener(this)
        fab_settings.setOnClickListener(this)
    }

    private fun setupDashboardViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        vp_db_cards.adapter = viewPagerAdapter
        vp_db_cards.pageMargin = dpToPx(8)
    }

    private fun setupWordList() {
        wordListAdapter = WordListAdapter()
        wordListAdapter.listOfWordList = getWordList()
        wordListAdapter.addWordListAction = {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val createWordListFragment = CreateWordListFragment.newInstance()
            createWordListFragment.show(fragmentTransaction, "create_wordlist_dialog")
        }
        wordListAdapter.wordListAction = { wordList ->
            startActivity(Intent(this, WordListActivity::class.java))
        }
        val gridLayoutManager = GridLayoutManager(this, 2)
        val itemOffsetDecoration = ItemOffsetDecoration(32)
        rv_word_list.adapter = wordListAdapter
        rv_word_list.layoutManager = gridLayoutManager
        rv_word_list.addItemDecoration(itemOffsetDecoration)
        rv_word_list.isNestedScrollingEnabled = false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_search_bar -> startActivity(Intent(this, SearchActivity::class.java))
            R.id.fab_practice -> startActivity(Intent(this, PracticeActivity::class.java))
            R.id.fab_related_words -> startActivity(Intent(this, RelatedWordsActivity::class.java))
            R.id.fab_marketplace -> startActivity(Intent(this, MarketplaceActivity::class.java))
            R.id.fab_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun getWordList(): MutableList<WordList> {
        val list = mutableListOf<WordList>()
        list.add(WordList(1, "", "", "Biology Words"))
        list.add(WordList(2, "", "", "Very Alternatives"))
        list.add(WordList(3, "", "", "Words Essentials"))
        list.add(WordList(4, "", "", "Words Advanced"))
        list.add(WordList(5, "", "", "Common 1000"))
        return list
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> WordOfDayFragment.newInstance()
                1 -> WordGameFragment.newInstance()
                2 -> WordOfDayFragment.newInstance()
                3 -> WordGameFragment.newInstance()
                else -> WordOfDayFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }
}
