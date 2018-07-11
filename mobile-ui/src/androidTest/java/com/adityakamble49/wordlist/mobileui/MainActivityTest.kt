package com.adityakamble49.wordlist.mobileui

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.mobileui.test.TestWordListApp
import com.adityakamble49.wordlist.mobileui.test.WordListDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubWordListRepositoryGetWordList(Flowable.just(listOf(WordListDataFactory.makeWordList())))
        activity.launchActivity(null)
    }

    private fun stubWordListRepositoryGetWordList(flowable: Flowable<List<WordList>>) {
        whenever(TestWordListApp.appComponent().wordListRepository().getWordLists()).thenReturn(
                flowable)
    }
}