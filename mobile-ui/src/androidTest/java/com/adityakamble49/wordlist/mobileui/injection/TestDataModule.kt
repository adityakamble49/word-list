package com.adityakamble49.wordlist.mobileui.injection

import com.adityakamble49.wordlist.domain.repository.WordListRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Test Data Module
 *
 * @author Aditya Kamble
 * @since 11/7/2018
 */
@Module
class TestDataModule {

    @Provides
    @Singleton
    fun providesDataRepository(): WordListRepository {
        return mock()
    }
}