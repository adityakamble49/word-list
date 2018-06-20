package com.adityakamble49.wordlist.ui.word

import android.view.View
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.R
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Word Activity Normal Mode Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
open class WordActivityNormalModeTest : WordActivityModeTest() {

    override fun getActivityMode() = WordActivity.Companion.WordActivityMode.NORMAL

    override fun getSpecifiedTitle(): String = wordActivity.getString(R.string.app_name)

    override fun getAvailableOptionMenus(): IntArray {
        val optionMenuArray = intArrayOf(R.id.action_edit_word)
        return optionMenuArray
    }

    override fun getControllerVisibility() = View.VISIBLE

    override fun shouldWordInfoEmpty() = false
}