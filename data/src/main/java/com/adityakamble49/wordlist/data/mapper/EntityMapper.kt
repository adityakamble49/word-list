package com.adityakamble49.wordlist.data.mapper

/**
 * Entity Mapper
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
interface EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(domain: D): E
}