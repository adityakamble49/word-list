package com.adityakamble49.wordlist.ui.word

import android.content.Intent
import com.adityakamble49.wordlist.BuildConfig
import kotlinx.android.synthetic.main.activity_word_info.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

/**
 * Word Activity Mode Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
abstract class WordActivityModeTest {

    protected lateinit var wordActivityController: ActivityController<WordActivity>

    @Before
    fun setUp() {
        val singleWordIntent = Intent(Intent.ACTION_VIEW)
        singleWordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE, getActivityMode().ordinal)
        wordActivityController = Robolectric.buildActivity(WordActivity::class.java,
                singleWordIntent).create().visible()
    }

    abstract fun getActivityMode(): WordActivity.Companion.WordActivityMode

    @Test
    fun shouldNotBeNull() {
        assertNotNull(wordActivityController)
    }

    @Test
    fun shouldHaveSpecifiedTitle() {
        Assert.assertEquals(getSpecifiedTitle(),
                wordActivityController.get().supportActionBar?.title)
    }

    abstract fun getSpecifiedTitle(): String

    @Test
    fun checkOptionMenuAvailable() {
        val availableOptionMenus = getAvailableOptionMenus()
        availableOptionMenus.forEach {
            val menuItem = shadowOf(wordActivityController.get()).optionsMenu.findItem(it)
            assertEquals(true, menuItem.isVisible)
        }
    }

    abstract fun getAvailableOptionMenus(): IntArray

    @Test
    fun checkBottomNavigationController() {
        assertEquals(getControllerVisibility(), wordActivityController.get().fab_dictate.visibility)
        assertEquals(getControllerVisibility(),
                wordActivityController.get().llBottomNavigation.visibility)
    }

    abstract fun getControllerVisibility(): Int

    @Test
    fun checkWordInfoEmpty() {
        if (shouldWordInfoEmpty()) {
            assertEquals("", wordActivityController.get().word_information.text.toString())
            assertEquals("", wordActivityController.get().word_mnemonic.text.toString())
        }
    }

    abstract fun shouldWordInfoEmpty(): Boolean
}