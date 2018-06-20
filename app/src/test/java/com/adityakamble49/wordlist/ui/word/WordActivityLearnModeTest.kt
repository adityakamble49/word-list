package com.adityakamble49.wordlist.ui.word

import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.R
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Word Activity Learn Mode Test
 *
 * @author Aditya Kamble
 * @since 20/6/2018
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class WordActivityLearnModeTest : WordActivityNormalModeTest() {

    override fun getSpecifiedTitle(): String = wordActivityController.get().getString(
            R.string.label_learn_mode)

    override fun getActivityMode() = WordActivity.Companion.WordActivityMode.LEARN
}