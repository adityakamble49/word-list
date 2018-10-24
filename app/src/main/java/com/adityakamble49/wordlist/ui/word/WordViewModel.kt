package com.adityakamble49.wordlist.ui.word

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.wordlist.cache.entities.Word
import com.adityakamble49.wordlist.repository.WordRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Word View Model
 *
 * @author Aditya Kamble
 * @since 23/10/2018
 */
class WordViewModel @Inject constructor(
        private val wordRepository: WordRepository) : ViewModel() {

    val wordInfo: MutableLiveData<Word> = MutableLiveData()
    private val wordInfoSubscriber = WordInfoSubscriber()

    fun fetchWordInfo(wordName: String) {
        val subscription = wordRepository.getWordInfo(wordName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(wordInfoSubscriber)
    }

    private inner class WordInfoSubscriber : SingleObserver<Word> {
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}

        override fun onSuccess(t: Word) {
            wordInfo.postValue(t)
        }
    }
}