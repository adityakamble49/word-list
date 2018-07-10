package com.adityakamble49.wordlist.presentation.mapper

interface Mapper<D, V> {

    fun mapToView(model: D): V

    fun mapFromView(view: V): D
}