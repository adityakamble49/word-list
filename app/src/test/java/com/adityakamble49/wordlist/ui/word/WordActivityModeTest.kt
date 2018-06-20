package com.adityakamble49.wordlist.ui.word

import android.content.Intent
import com.adityakamble49.wordlist.BuildConfig
import com.adityakamble49.wordlist.R
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
    protected lateinit var wordActivity: WordActivity

    @Before
    fun setUp() {
        val singleWordIntent = Intent(Intent.ACTION_VIEW)
        singleWordIntent.putExtra(WordActivity.IE_KEY_WORD_ACTIVITY_MODE, getActivityMode().ordinal)
        wordActivityController = Robolectric.buildActivity(WordActivity::class.java,
                singleWordIntent).create().visible()
        wordActivity = wordActivityController.get()
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

    @Test
    fun checkWordInfoEmpty() {
        if (shouldWordInfoEmpty()) {
            assertEquals("", wordActivity.word_information.text.toString())
            assertEquals("", wordActivity.word_mnemonic.text.toString())
        }
    }

    abstract fun shouldWordInfoEmpty(): Boolean

    @Test
    fun checkEditMode() {
        val availableOptionMenus = getAvailableOptionMenus()
        if (availableOptionMenus.contains(R.id.action_edit_word)) {

            // Prepare Edit and Submit Menu
            val editMenuItem = shadowOf(wordActivity).optionsMenu.findItem(R.id.action_edit_word)
            val submitMenuItem = shadowOf(wordActivity).optionsMenu.findItem(
                    R.id.action_submit_word)

            // Check if Edit and Submit Menu Visibility
            assertEquals(true, editMenuItem.isVisible)
            assertEquals(false, submitMenuItem.isVisible)

            // Perform On Click Edit Menu Item
            wordActivityController.get().onOptionsItemSelected(editMenuItem)

            // Check if Word Info is editable
            assertEquals(true, wordActivity.word_name.isEnabled)
            assertEquals(true, wordActivity.word_type.isEnabled)
            assertEquals(true, wordActivity.word_pronunciation.isEnabled)
            assertEquals(true, wordActivity.word_information.isEnabled)
            assertEquals(true, wordActivity.word_mnemonic.isEnabled)
            assertEquals(true, wordActivity.word_information.isCursorVisible)
            assertEquals(true, wordActivity.word_mnemonic.isCursorVisible)

            // Check if Edit and Submit Menu Visibility
            assertEquals(false, editMenuItem.isVisible)
            assertEquals(true, submitMenuItem.isVisible)
        }
    }
}