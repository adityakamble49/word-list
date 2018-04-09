package com.adityakamble49.wordlist.di.module.list

import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListJoinRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.ui.list.WordListViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Module
class WordListFragmentModule {

    @Provides
    fun provideWordListViewModelFactory(preferenceHelper: PreferenceHelper,
                                        wordRepo: WordRepo,
                                        wordListJoinRepo: WordListJoinRepo) =
            WordListViewModelFactory(preferenceHelper, wordRepo, wordListJoinRepo)

}