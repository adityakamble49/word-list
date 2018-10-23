package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.BuildConfig

/**
 * Remote Urls
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object RemoteUrls {
    const val BASE_URL = "https://owlbot.info/api/v2/"
    const val WORD_INFO_BASE_URL = "https://api.mlab.com/api/1/databases/word-list-db/collections/word-data?"
    const val WORD_INFO_API_KEY = BuildConfig.MLabApiKey
    const val MNEMONICS_API_URL = "https://mnemonicdictionary.com/?word="
    const val GITHUB_AUTH_TOKEN = BuildConfig.GitHubApiKey
    private const val GITHUB_BASE = "https://api.github.com/"
    const val GITHUB_WORDLIST = GITHUB_BASE + "repos/adityakamble49/wordlist-marketplace/contents/wordlists"
    const val IMAGE_SEARCH = "https://www.google.com/search?tbm=isch&q="

    const val RELATED_WORDS_BASIC = "https://api.datamuse.com/words?ml="
    const val RELATED_WORDS_DESCRIBE = "https://api.datamuse.com/words?rel_jjb="
    const val RELATED_WORDS_ADJECTIVE = "https://api.datamuse.com/words?rel_jja="
    const val RELATED_WORDS_TRIGGERED = "https://api.datamuse.com/words?rel_trg="

    fun getWordKeyVal(word: String) = "{\"name\":\"$word\"}"

    fun getRelatedWordsBasicUrl(word: String) = "$RELATED_WORDS_BASIC$word"
    fun getRelatedWordsDescribeUrl(word: String) = "$RELATED_WORDS_DESCRIBE$word"
    fun getRelatedWordsAdjectiveUrl(word: String) = "$RELATED_WORDS_ADJECTIVE$word"
    fun getRelatedWordsTriggeredUrl(word: String) = "$RELATED_WORDS_TRIGGERED$word"
}