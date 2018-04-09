package com.adityakamble49.wordlist.cache.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Converter for Room
 *
 * @author Aditya Kamble
 * @since 10/4/2018
 */
class Converter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}