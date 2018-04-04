package com.adityakamble49.wordlist.utils

import android.content.Context
import com.adityakamble49.wordlist.R
import com.adityakamble49.wordlist.di.qualifier.ApplicationContext
import com.adityakamble49.wordlist.di.scope.PerApplication
import com.adityakamble49.wordlist.model.Word
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader


/**
 * @author Aditya Kamble
 * @since 5/4/2018
 */
@PerApplication
class DataProcessor @Inject constructor(@ApplicationContext var context: Context) {

    private lateinit var gsonParser: Gson

    init {
        gsonParser = getGson()
    }

    fun parseWordList(): List<Word> {
        val wordFileInputStream = context.resources.openRawResource(
                R.raw.manhattan_essential_with_mnemonic)
        val bufferedReader = BufferedReader(InputStreamReader(wordFileInputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.lineSequence().forEach { stringBuilder.append(it) }
        val listType = object : TypeToken<List<Word>>() {}.type
        return gsonParser.fromJson(stringBuilder.toString(), listType)
    }

    private fun getGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
}