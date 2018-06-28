package com.adityakamble49.wordlist.ui.word

import android.view.View
import com.adityakamble49.wordlist.BuildConfig
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Word Activity Single Mode Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class WordActivitySingleModeTest : WordActivityNormalModeTest() {

    override fun getActivityMode() = WordActivity.Companion.WordActivityMode.SINGLE

    override fun getAvailableOptionMenus() = intArrayOf()

    override fun getControllerVisibility() = View.GONE
}