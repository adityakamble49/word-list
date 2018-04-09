package com.adityakamble49.wordlist.ui.list

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.PreferenceHelper
import com.adityakamble49.wordlist.cache.db.WordListJoinRepo
import com.adityakamble49.wordlist.cache.db.WordRepo
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordList
import com.adityakamble49.wordlist.model.WordListTransformation
import com.adityakamble49.wordlist.model.WordListTransformationType
import javax.inject.Inject

/**
 * Word List ViewModel
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
class WordListViewModel @Inject constructor(
        private val preferenceHelper: PreferenceHelper,
        private val wordRepo: WordRepo,
        private val wordListJoinRepo: WordListJoinRepo) : ViewModel() {

    private val wordList: LiveData<List<Word>>
    private val wordListTransformations = MutableLiveData<WordListTransformation>()
    private val defaultWordListTransformation = WordListTransformation(
            WordListTransformationType.WORD_LIST_TYPE, preferenceHelper.currentListType, null)

    init {
        wordListTransformations.postValue(defaultWordListTransformation)
        wordList = Transformations.switchMap(wordListTransformations,
                Function<WordListTransformation, LiveData<List<Word>>> {
                    return@Function when (it.wordListTransformationType) {
                        WordListTransformationType.WORD_LIST_TYPE -> it.wordListType?.let {
                            return@let wordRepo.getWordList(it)
                        }
                        WordListTransformationType.SAVED_WORD_LIST -> it.wordListSaved?.let {
                            return@let wordListJoinRepo.getWords(it.id)
                        }
                    }
                })
    }

    fun getWordList() = wordList

    fun updateCurrentWordListType(wordListType: Int) {
        val updatedWordListTransformation = WordListTransformation(
                WordListTransformationType.WORD_LIST_TYPE, wordListType, null)
        this.wordListTransformations.value = updatedWordListTransformation
    }

    fun updateCurrentLoadedSavedList(selectedSavedWordList: WordList) {
        val updatedWordListTransformation = WordListTransformation(
                WordListTransformationType.SAVED_WORD_LIST, 0, selectedSavedWordList)
        this.wordListTransformations.value = updatedWordListTransformation
    }
}