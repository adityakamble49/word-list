package com.adityakamble49.wordlist.remote.utils;

public class Constants {

    public static class RemoteUrls {
        public static final String BASE_URL = "https://owlbot.info/api/v2/";
        public static final String MNEMONICS_API_URL = "https://mnemonicdictionary.com/?word=";
        public static final String GITHUB_AUTH_TOKEN = "GitHubApiKey";
        private static final String GITHUB_BASE = "https://api.github.com/";
        public static final String GITHUB_WORDLIST = GITHUB_BASE + "repos/adityakamble49/wordlist-marketplace/contents/wordlists";
    }

    public static class JSoupQueries {
        public static final String MNEMONIC_CARD = "div.mnemonic-card div.card-text";
        public static final String MNEMONIC_ATTRIBUTION = "p.md-attribution";
    }
}
