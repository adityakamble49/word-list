package com.adityakamble49.wordlist.mobileui.injection.module

import com.adityakamble49.wordlist.data.WordListDataRepository
import com.adityakamble49.wordlist.domain.repository.WordListRepository
import dagger.Binds
import dagger.Module


/**
 * Data Module
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: WordListDataRepository): WordListRepository
}