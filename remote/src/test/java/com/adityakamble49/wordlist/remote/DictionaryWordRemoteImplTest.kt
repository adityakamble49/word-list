package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.remote.mapper.DictionaryWordMapper
import com.adityakamble49.wordlist.remote.model.DictionaryWord
import com.adityakamble49.wordlist.remote.service.WordListService
import com.adityakamble49.wordlist.remote.test.RemoteDataFactory
import com.nhaarman.mockito_kotlin.any
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
    fun getDictionaryWord() {
        stubWordListServiceGetWordInformation(Single.just(RemoteDataFactory.makeDictionaryWords(4)))
        stubDictionaryWordMapper(any(), RemoteDataFactory.makeDictionaryWordEntity())
        val testObserver = dictionaryWordRemoteImpl.getDictionaryWord("").test()
        testObserver.assertComplete()
    }

    private fun stubWordListServiceGetWordInformation(single: Single<List<DictionaryWord>>) {
        whenever(wordListService.getWordInformation(any())).thenReturn(single)
    }

    private fun stubDictionaryWordMapper(model: DictionaryWord,
                                         entity: DictionaryWordEntity) {
        whenever(mapper.mapFromModel(model)).thenReturn(entity)
    }
}