package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.TypeConverter
import com.adityakamble49.wordlist.cache.entities.WordInformation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converter for Room
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
class Converter {
    @TypeConverter
    fun toArrayListOfInt(value: String): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListOfInt(list: ArrayList<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toArrayListOfWordInfo(value: String): ArrayList<WordInformation> {
        val listType = object : TypeToken<ArrayList<WordInformation>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListOfWordInfo(list: ArrayList<WordInformation>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}