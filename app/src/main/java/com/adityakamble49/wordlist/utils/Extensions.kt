package com.adityakamble49.wordlist.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.regex.Pattern

/**
 * Common Extensions
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */


val specialCharPattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)


fun getGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

fun String.hasSpecialChar(): Boolean {
    val matcher = specialCharPattern.matcher(this)
    return matcher.find()
}

fun getMnemonicAPIUrl(word: String): String {
    return "${Constants.RemoteUrls.MNEMONICS_API_URL}$word"
}