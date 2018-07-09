package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.remote.mapper.DictionaryWordMapper
import com.adityakamble49.wordlist.remote.model.DictionaryWord
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
 * Dictionary Word Remote Implementation Test
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
@RunWith(JUnit4::class)
class DictionaryWordRemoteImplTest {

    @Mock private lateinit var wordListService: WordListService
    @Mock private lateinit var mapper: DictionaryWordMapper
    private lateinit var dictionaryWordRemoteImpl: DictionaryWordRemoteImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dictionaryWordRemoteImpl = DictionaryWordRemoteImpl(wordListService, mapper)
    }

    @Test
    fun getDictionaryWordCompletes() {
        stubWordListServiceGetWordInformation(Single.just(RemoteDataFactory.makeDictionaryWords(4)))
        stubDictionaryWordMapper(any(), RemoteDataFactory.makeDictionaryWordEntity())
        val testObserver = dictionaryWordRemoteImpl.getDictionaryWord(DataFactory.randomString())
                .test()
        testObserver.assertComplete()
    }

    @Test
    fun getDictionaryWordCallServer() {
        val word = DataFactory.randomString()
        stubWordListServiceGetWordInformation(Single.just(RemoteDataFactory.makeDictionaryWords(4)))
        stubDictionaryWordMapper(any(), RemoteDataFactory.makeDictionaryWordEntity())
        dictionaryWordRemoteImpl.getDictionaryWord(word).test()
        verify(wordListService).getWordInformation(word)
    }

    @Test
    fun getDictionaryWordReturnsData() {
        // Stub Dictionary Word List response
        val word = DataFactory.randomString()
        val dictionaryWordList = RemoteDataFactory.makeDictionaryWords(4)
        stubWordListServiceGetWordInformation(Single.just(dictionaryWordList))

        // Stub Dictionary word list mapping to entity
        val dictionaryWordEntityList = mutableListOf<DictionaryWordEntity>()
        dictionaryWordList.forEach {
            val dictionaryWordEntity = RemoteDataFactory.makeDictionaryWordEntity()
            dictionaryWordEntityList.add(dictionaryWordEntity)
            stubDictionaryWordMapper(it, dictionaryWordEntity)
        }

        // Test getDictionaryWord response
        val testObserver = dictionaryWordRemoteImpl.getDictionaryWord(word).test()
        testObserver.assertValues(dictionaryWordEntityList)
    }

    private fun stubWordListServiceGetWordInformation(single: Single<List<DictionaryWord>>) {
        whenever(wordListService.getWordInformation(any())).thenReturn(single)
    }

    private fun stubDictionaryWordMapper(model: DictionaryWord,
                                         entity: DictionaryWordEntity) {
        whenever(mapper.mapFromModel(model)).thenReturn(entity)
    }
}