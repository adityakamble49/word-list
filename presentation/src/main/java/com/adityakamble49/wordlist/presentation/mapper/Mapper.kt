package com.adityakamble49.wordlist.presentation.mapper

interface Mapper<in D, out V> {

    fun mapToView(model: D): V
}