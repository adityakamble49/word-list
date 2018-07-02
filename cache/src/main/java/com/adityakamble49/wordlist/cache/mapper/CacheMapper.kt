package com.adityakamble49.wordlist.cache.mapper

interface CacheMapper<C, E> {

    fun mapFromCache(cache: C): E

    fun mapToCache(entity: E): C
}