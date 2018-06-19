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
import org.robolectric.annotation.Config
import timber.log.Timber
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Word Activity Mode Test
 *
 * @author Aditya Kamble
 * @since 19/6/2018
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
abstract class WordActivityModeTest {

    protected lateinit var wordActivity: WordActivity

    @Before
    fun setUp() {
        val singleWordIntent = Intent(Intent.ACTION_VIEW)
        singleWordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE, getActivityMode())
        wordActivity = Robolectric.buildActivity(WordActivity::class.java,
                singleWordIntent).create().visible().get()
        wordActivity.initializeActivityMode(getActivityMode().ordinal)
    }

    abstract fun getActivityMode(): WordActivity.Companion.WordActivityMode

    @Test
    fun shouldNotBeNull() {
        assertNotNull(wordActivity)
    }

    @Test
    fun shouldHaveSpecifiedTitle() {
        Assert.assertEquals(getSpecifiedTitle(), wordActivity.supportActionBar?.title)
    }

    abstract fun getSpecifiedTitle(): String

    @Test
    fun checkOptionMenuAvailable() {
        val availableOptionMenus = getAvailableOptionMenus()
        availableOptionMenus.forEach {
            val menuItem = shadowOf(wordActivity).optionsMenu.findItem(it)
            assertEquals(true, menuItem.isVisible)
        }
    }

    abstract fun getAvailableOptionMenus(): IntArray

    @Test
    fun checkBottomNavigationController() {
        assertEquals(getControllerVisibility(), wordActivity.fab_dictate.visibility)
        assertEquals(getControllerVisibility(), wordActivity.llBottomNavigation.visibility)
    }

    abstract fun getControllerVisibility(): Int
}