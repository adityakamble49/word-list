package com.adityakamble49.wordlist.presentation.wordlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.domain.interactor.CreateWordList
import com.adityakamble49.wordlist.domain.interactor.GetWordLists
import com.adityakamble49.wordlist.domain.model.WordList
import com.adityakamble49.wordlist.presentation.mapper.WordListMapper
import com.adityakamble49.wordlist.presentation.model.WordListView
import com.adityakamble49.wordlist.presentation.state.Resource
import com.adityakamble49.wordlist.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber
import javax.inject.Inject

/**
 * Word List ViewModel
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
class WordListViewModel @Inject constructor(
        private val mapper: WordListMapper,
        private val createWordList: CreateWordList,
        private val getWordLists: GetWordLists) : ViewModel() {

    private val wordListsLiveData: MutableLiveData<Resource<List<WordListView>>> = MutableLiveData()

    init {
        fetchWordLists()
    }

    override fun onCleared() {
        createWordList.dispose()
        super.onCleared()
    }

    fun getWordLists(): LiveData<Resource<List<WordListView>>> {
        return wordListsLiveData
    }

    fun fetchWordLists() {
        wordListsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getWordLists.execute(GetWordListsSubscriber())
    }

    private inner class GetWordListsSubscriber : DisposableObserver<List<WordList>>() {
        override fun onComplete() {}
        override fun onNext(t: List<WordList>) {
            wordListsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) },
                    null))
        }

        override fun onError(e: Throwable) {
            wordListsLiveData.postValue(Resource(ResourceState.ERROR,
                    null,
                    e.localizedMessage))
        }
    }

    fun addWordList(wordListName: String) {
        createWordList.execute(CreateWordListSubscriber(),
                CreateWordList.Params.forWordList(wordListName))
    }

    private inner class CreateWordListSubscriber : DisposableSingleObserver<WordList>() {

        override fun onSuccess(t: WordList) {
            Timber.i(mapper.mapToView(t).toString())
        }

        override fun onError(e: Throwable) {
        }
    }
}