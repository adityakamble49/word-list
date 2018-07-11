package com.adityakamble49.wordlist.mobileui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.mobileui.test.TestWordListApp
import com.adityakamble49.wordlist.mobileui.test.WordListDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Test
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubWordListRepositoryGetWordList(Flowable.just(listOf(WordListDataFactory.makeWordList())))
        activity.launchActivity(null)
    }

    @Test
    fun wordListDisplays() {
        val wordLists = WordListDataFactory.makeWordLists(20)
        stubWordListRepositoryGetWordList(Flowable.just(wordLists))
        activity.launchActivity(null)

        wordLists.forEachIndexed { index, wordList ->
            onView(withId(R.id.rv_wordlist)).perform(
                    RecyclerViewActions.scrollToPosition<WordListAdapter.ViewHolder>(index))
            onView(withId(R.id.rv_wordlist))
                    .check(matches(hasDescendant(withText(wordList.name))))
        }
    }

    @Test
    fun createWordListDisplays() {
        val wordLists = WordListDataFactory.makeWordLists(0)
        stubWordListRepositoryGetWordList(Flowable.just(wordLists))
        val wordList = WordListDataFactory.makeWordList()
        stubWordListRepositoryCreateWordList(Single.just(wordList))
        activity.launchActivity(null)
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.rv_wordlist)).perform(
                RecyclerViewActions.scrollToPosition<WordListAdapter.ViewHolder>(0))
        onView(withId(R.id.rv_wordlist)).check(matches(hasDescendant(withText(wordList.name))))
    }

    private fun stubWordListRepositoryGetWordList(flowable: Flowable<List<WordList>>) {
        whenever(TestWordListApp.appComponent().wordListRepository().getWordLists()).thenReturn(
                flowable)
    }

    private fun stubWordListRepositoryCreateWordList(single: Single<WordList>) {
        whenever(TestWordListApp.appComponent().wordListRepository().createWordList(
                any())).thenReturn(single)
    }
}