package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.WordEntity
import com.adityakamble49.wordlist.remote.mapper.WordMapper
import com.adityakamble49.wordlist.remote.model.Word
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.test.DataFactory
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Word Remote Implementation Test
 *
 * @author Aditya Kamble
 * @since 9/7/2018
 */
@RunWith(JUnit4::class)
class WordRemoteImplTest {

    @Mock private lateinit var wordListService: WordListService
    @Mock private lateinit var mapper: WordMapper
    private lateinit var wordRemoteImpl: WordRemoteImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        wordRemoteImpl = WordRemoteImpl(wordListService, mapper)
    }

    @Test
    fun getWordsCompletes() {
        stubWordListServiceGetWordsFromMarketplace(Single.just(RemoteDataFactory.makeWords(4)))
        stubWordMapper(any(), RemoteDataFactory.makeWordEntity())
        val testObserver = wordRemoteImpl.getWords(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getWordsCallServer() {
        stubWordListServiceGetWordsFromMarketplace(Single.just(RemoteDataFactory.makeWords(4)))
        stubWordMapper(any(), RemoteDataFactory.makeWordEntity())
        wordRemoteImpl.getWords(DataFactory.randomString()).test()
        verify(wordListService).getWordsFromMarketplace(any(), any())
    }

    @Test
    fun getWordsCallReturnsData() {
        // Stub getWordsFromMarketplace
        val words = RemoteDataFactory.makeWords(10)
        stubWordListServiceGetWordsFromMarketplace(Single.just(words))

        // Stub Word Mapper
        val wordEntityList = mutableListOf<WordEntity>()
        words.forEach {
            val wordEntity = RemoteDataFactory.makeWordEntity()
            wordEntityList.add(wordEntity)
            stubWordMapper(it, wordEntity)
        }

        // Test Get Words
        val testObserver = wordRemoteImpl.getWords(DataFactory.randomString()).test()
        testObserver.assertValues(wordEntityList)
    }

    private fun stubWordListServiceGetWordsFromMarketplace(single: Single<List<Word>>) {
        whenever(wordListService.getWordsFromMarketplace(any(), any())).thenReturn(single)
    }

    private fun stubWordMapper(model: Word, entity: WordEntity) {
        whenever(mapper.mapFromModel(model)).thenReturn(entity)
    }
}