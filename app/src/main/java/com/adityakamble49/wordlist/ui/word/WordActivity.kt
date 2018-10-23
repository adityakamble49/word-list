package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.ui.common.BaseInjectableActivity
import com.adityakamble49.wordlist.ui.related.RelatedWordsActivity
import kotlinx.android.synthetic.main.activity_word.*
import kotlinx.android.synthetic.main.layout_word_body.*
import kotlinx.android.synthetic.main.layout_word_etymology_card.*
import kotlinx.android.synthetic.main.layout_word_header.*
import kotlinx.android.synthetic.main.layout_word_info_card.*
import javax.inject.Inject

/**
 * Word Activity
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class WordActivity : BaseInjectableActivity(), View.OnClickListener {

    companion object {
        const val WORD_NAME: String = "word_name"
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        setupViewModels()
        bindView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_related_words -> startActivity(Intent(this, RelatedWordsActivity::class.java))
            R.id.fab_word_image -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val wordImageFragment = WordImageFragment.newInstance()
                wordImageFragment.show(fragmentTransaction, "word_image_dialog")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.word_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModels() {
        wordViewModel = ViewModelProviders.of(this, viewModelFactory).get(WordViewModel::class.java)
    }

    private fun bindView() {
        setupToolbar()
        setupWordActions()
        setupWordInfo()
    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        // Enable Title only on Collapse
        collapsing_toolbar.title = resources.getString(R.string.word_name_default)
        var isShow = true
        var scrollRange = -1
        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsing_toolbar.title = resources.getString(R.string.word_name_default)
                isShow = true
            } else if (isShow) {
                collapsing_toolbar.title = ""
                isShow = false
            }
        }
    }

    private fun setupWordActions() {
        fab_related_words.setOnClickListener(this)
        fab_word_image.setOnClickListener(this)
    }

    private fun setupWordInfo() {
        val wordName = intent.getStringExtra(WORD_NAME)
        wordName?.let {
            wordViewModel.fetchWordInfo(it)
        }
        wordViewModel.wordInfo.observe(this,
                Observer<Word> { t ->
                    t?.let {
                        tv_word_name.text = it.name
                        tv_word_pronunciation.text = it.pronunciation
                        tv_word_mnemonics_value.text = it.etymology
                        tv_word_info_type_value.text = it.information[0].type
                        tv_word_info_definition_value.text = it.information[0].definition
                        tv_word_info_example_value.text = it.information[0].example
                    }
                })
    }
}
