package com.adityakamble49.wordlist.remote

import com.adityakamble49.wordlist.BuildConfig

/**
 * Remote Urls
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
object RemoteUrls {
    val BASE_URL = "https://owlbot.info/api/v2/"
    val MNEMONICS_API_URL = "https://mnemonicdictionary.com/?word="
    val GITHUB_AUTH_TOKEN = BuildConfig.GitHubApiKey
    private val GITHUB_BASE = "https://api.github.com/"
    val GITHUB_WORDLIST = GITHUB_BASE + "repos/adityakamble49/wordlist-marketplace/contents/wordlists"
    val IMAGE_SEARCH = "https://www.google.com/search?tbm=isch&q="
}