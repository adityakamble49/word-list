package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.DictionaryWordEntity
import com.adityakamble49.wordlist.remote.model.DictionaryWord
import javax.inject.Inject

/**
 * DictionaryWord Word List Mapper
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class DictionaryWordListMapper @Inject constructor() :
        Mapper<DictionaryWord, DictionaryWordEntity> {

    override fun mapFromModel(model: DictionaryWord): DictionaryWordEntity {
        val definition = model.definition ?: ""
        val type = model.type ?: ""
        val example = model.example ?: ""
        return DictionaryWordEntity(definition, type, example)
    }
}