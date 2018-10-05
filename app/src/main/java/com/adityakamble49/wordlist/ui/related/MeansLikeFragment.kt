package com.adityakamble49.wordlist.ui.related

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.cache.entities.Word
import kotlinx.android.synthetic.main.fragment_means_like.view.*
import java.util.*


/**
 * Means Like Fragment
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class MeansLikeFragment : Fragment() {

    private lateinit var relatedWordsAdapter: RelatedWordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_means_like, container, false)
        bindView(view)
        return view
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = MeansLikeFragment()
    }

    private fun bindView(view: View) {
        with(view) {
            relatedWordsAdapter = RelatedWordsAdapter()
            relatedWordsAdapter.listOfWord = getWords(100)
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(getSpanCount(85),
                    StaggeredGridLayoutManager.HORIZONTAL)
            rv_means_like_words.adapter = relatedWordsAdapter
            rv_means_like_words.layoutManager = staggeredGridLayoutManager
        }
    }

    private fun getWords(count: Int): MutableList<Word> {
        val random = Random()
        val list = mutableListOf<Word>()
        repeat(count) {
            list.add(Word(0, getWordString(random, random.nextInt(10) + 5), "", arrayListOf()))
        }
        return list
    }

    private fun getWordString(random: Random, length: Int): String {
        val input = "abcdefghijklmnopqrstuvwxyz"
        val sb = StringBuilder()
        repeat(length) {
            sb.append(input[random.nextInt(26)])
        }
        return sb.toString()
    }

    private fun getSpanCount(itemHeight: Int): Int {
        val displayMetrics = (context as Context).resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        return (dpHeight / itemHeight).toInt()
    }
}