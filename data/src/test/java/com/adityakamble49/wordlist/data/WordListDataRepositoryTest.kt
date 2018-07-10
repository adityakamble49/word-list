package com.adityakamble49.wordlist.data

import com.adityakamble49.wordlist.data.mapper.WordListMapper
import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.data.repository.WordListDataStore
import com.adityakamble49.wordlist.data.store.WordListDataStoreFactory
import com.adityakamble49.wordlist.data.test.DataFactory
import com.adityakamble49.wordlist.data.test.WordListDataFactory
import com.adityakamble49.wordlist.domain.model.WordList
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class WordListDataRepositoryTest {

    @Mock private lateinit var mapper: WordListMapper
    @Mock private lateinit var factory: WordListDataStoreFactory
    @Mock private lateinit var store: WordListDataStore
    private lateinit var wordListDataRepository: WordListDataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        wordListDataRepository = WordListDataRepository(mapper, factory)
        stubFactoryGetDataStore()
    }

    @Test
    fun createWordListCompletes() {
        stubSaveWordList(Single.just(DataFactory.randomLong()))
        stubMapperToEntity(WordListDataFactory.makeWordListEntity(), any())
        val testObserver = wordListDataRepository.createWordList(WordListDataFactory.makeWordList())
                .test()
        testObserver.assertComplete()
    }

    @Test
    fun createWordListReturnsData() {
        val wordListId = DataFactory.randomLong()
        val wordList = WordListDataFactory.makeWordList()
        stubSaveWordList(Single.just(wordListId))
        stubMapperToEntity(WordListDataFactory.makeWordListEntity(), any())
        val testObserver = wordListDataRepository.createWordList(wordList).test()
        wordList.id = wordListId.toInt()
        testObserver.assertValue(wordList)
    }

    @Test
    fun getWordListsCompletes() {
        stubGetWordLists(Flowable.just(WordListDataFactory.makeWordListEntityList(3)))
        val testObserver = wordListDataRepository.getWordLists().test()
        testObserver.assertComplete()
    }

    @Test
    fun getWordListsReturnsData() {
        val wordListEntity = WordListDataFactory.makeWordListEntity()
        val wordList = WordListDataFactory.makeWordList()
        stubGetWordLists(Flowable.just(listOf(wordListEntity)))
        stubMapperFromEntity(wordListEntity, wordList)
        val testObserver = wordListDataRepository.getWordLists().test()
        testObserver.assertValue(listOf(wordList))
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore()).thenReturn(store)
    }

    private fun stubSaveWordList(single: Single<Long>) {
        whenever(store.saveWordList(any())).thenReturn(single)
    }

    private fun stubGetWordLists(flowable: Flowable<List<WordListEntity>>) {
        whenever(store.getWordLists()).thenReturn(flowable)
    }

    private fun stubMapperToEntity(entity: WordListEntity, model: WordList) {
        whenever(mapper.mapToEntity(model)).thenReturn(entity)
    }

    private fun stubMapperFromEntity(entity: WordListEntity, model: WordList) {
        whenever(mapper.mapFromEntity(entity)).thenReturn(model)
    }
}