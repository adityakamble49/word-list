package com.adityakamble49.wordlist.ui.word

import android.view.View
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.R
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Word Activity Practice Mode Test
 *
 * @author Aditya Kamble
 * @since 20/6/2018
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class WordActivityPracticeModeTest : WordActivityModeTest() {

    override fun getSpecifiedTitle(): String = wordActivity.getString(R.string.label_practice_mode)

    override fun getAvailableOptionMenus(): IntArray {
        val optionMenuArray = intArrayOf(R.id.action_show_info)
        return optionMenuArray
    }

    override fun getActivityMode() = WordActivity.Companion.WordActivityMode.PRACTICE

    override fun getControllerVisibility() = View.VISIBLE

    override fun shouldWordInfoEmpty() = true
}