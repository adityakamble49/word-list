package com.adityakamble49.wordlist.utils

import android.content.Context
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.model.Word
import com.adityakamble49.wordlist.model.WordListType
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject


/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
@PerApplication
class DataProcessor @Inject constructor(@ApplicationContext var context: Context) {

    private var gsonParser: Gson

    init {
        gsonParser = getGson()
    }

    fun parseWordList(): List<Word> {
        val essentialWordList = parseSingleWordList(WordListType.MANHATTAN_ESSENTIAL)
        val advancedWordList = parseSingleWordList(WordListType.MANHATTAN_ADVANCED)
        val totalList = mutableListOf<Word>()
        totalList.addAll(essentialWordList)
        totalList.addAll(advancedWordList)
        return totalList
    }

    private fun parseSingleWordList(wordListType: WordListType): List<Word> {

        // Get required file from raw
        val wordFileInputStream = context.resources.openRawResource(when (wordListType) {
            WordListType.MANHATTAN_ESSENTIAL -> R.raw.manhattan_essential_with_mnemonic
            else -> R.raw.manhattan_essential_with_mnemonic
        })

        // Read and append content to list
        val bufferedReader = BufferedReader(InputStreamReader(wordFileInputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.lineSequence().forEach { stringBuilder.append(it) }
        val listType = object : TypeToken<List<Word>>() {}.type
        val wordList: List<Word> = gsonParser.fromJson(stringBuilder.toString(), listType)

        // Append word list type
        when (wordListType) {
            WordListType.MANHATTAN_ESSENTIAL -> wordList.forEach {
                it.listType = WordListType.MANHATTAN_ESSENTIAL.ordinal
            }
            WordListType.MANHATTAN_ADVANCED -> wordList.forEach {
                it.listType = WordListType.MANHATTAN_ADVANCED.ordinal
            }
        }
        return wordList
    }

    private fun getGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
}