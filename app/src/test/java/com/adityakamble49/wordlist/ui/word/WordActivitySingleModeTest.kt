package com.adityakamble49.wordlist.ui.word

import android.content.Intent
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.R
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Word Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
open class WordActivitySingleModeTest {

    private lateinit var singleWordActivity: WordActivity

    @Before
    fun setUp() {
        val singleWordIntent = Intent(Intent.ACTION_VIEW)
        singleWordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE,
                WordActivity.Companion.WordActivityMode.SINGLE)
        singleWordActivity = Robolectric.buildActivity(WordActivity::class.java,
                singleWordIntent).create().get()
    }

    @Test
    fun shouldNotBeNull() {
        assertNotNull(singleWordActivity)
    }

    @Test
    fun shouldHaveWordListAsTitle() {
        Assert.assertEquals(singleWordActivity.getString(R.string.app_name),
                singleWordActivity.supportActionBar?.title)
    }
}