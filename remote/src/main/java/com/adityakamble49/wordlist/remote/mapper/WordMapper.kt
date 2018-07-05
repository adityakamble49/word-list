package com.adityakamble49.wordlist.remote.mapper

import com.adityakamble49.wordlist.data.model.WordEntity
import com.adityakamble49.wordlist.remote.model.Word
import javax.inject.Inject

/**
 * Word Mapper
 *
 * @author Aditya Kamble
 * @since 5/7/2018
 */
class WordMapper @Inject constructor() :
        Mapper<Word, WordEntity> {

    override fun mapFromModel(model: Word): WordEntity {
        return WordEntity(0, 0, model.name, model.type, model.pronunciation, model.information,
                model.mnemonic)
    }
}