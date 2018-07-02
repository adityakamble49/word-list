package com.adityakamble49.wordlist.data.mapper

import com.adityakamble49.wordlist.data.model.WordListEntity
import com.adityakamble49.wordlist.domain.model.WordList

class WordListMapper : EntityMapper<WordListEntity, WordList> {

    override fun mapFromEntity(entity: WordListEntity): WordList {
        return WordList(entity.id, entity.hash, entity.marketplaceFilename, entity.name,
                entity.lastWordId)
    }

    override fun mapToEntity(domain: WordList): WordListEntity {
        return WordListEntity(domain.id, domain.hash, domain.marketplaceFilename, domain.name,
                domain.lastWordId)
    }
}