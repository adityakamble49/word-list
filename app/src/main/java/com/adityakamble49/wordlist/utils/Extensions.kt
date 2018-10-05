package com.adityakamble49.wordlist.utils

import android.app.Activity
import android.util.DisplayMetrics
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.regex.Pattern


/**
 * Common Extensions
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */


val specialCharPattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)


fun getGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

fun String.hasSpecialChar(): Boolean {
    val matcher = specialCharPattern.matcher(this)
    return matcher.find()
}

fun Activity.dpToPx(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}