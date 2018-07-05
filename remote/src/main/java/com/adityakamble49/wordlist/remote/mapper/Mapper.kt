package com.adityakamble49.wordlist.remote.mapper

interface Mapper<in M, out E> {

    fun mapFromModel(model: M): E
}