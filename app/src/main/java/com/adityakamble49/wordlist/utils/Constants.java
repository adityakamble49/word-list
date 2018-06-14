package com.adityakamble49.wordlist.utils;

/**
 * Constants
 *
 * @author Aditya Kamble
 * @since 8/4/2018
 */
public class Constants {

    public static class Database {
        public static final String DB_NAME = "word_list.db";
        public static final String TABLE_WORD_NAME = "words";
        public static final String TABLE_WORD_LISTS = "word_lists";
        public static final String TABLE_WORD_LIST_JOIN = "word_list_join";

        public static final String ENTRY_WORD_LIST_DEFAULT_1 = "Manhattan Essential - Alphabetical";
        public static final String ENTRY_WORD_LIST_DEFAULT_2 = "Manhattan Advanced - Alphabetical";
    }

    public static class DictateModeSpeedValues {
        public static final float SLOWER = 0.6f;
        public static final float SLOW = 0.8f;
        public static final float NORMAL = 1f;
        public static final float FAST = 1.5f;
        public static final float FASTER = 1.5f;
    }

    public static class RemoteUrls {
        public static final String BASE_URL = "https://owlbot.info/api/v2/";
        public static final String MNEMONICS_API_URL = "https://mnemonicdictionary.com/?word=";
    }

    public static class JSoupQueries {
        public static final String MNEMONIC_CARD = "div.mnemonic-card div.card-text";
        public static final String MNEMONIC_ATTRIBUTION = "p.md-attribution";
    }

    public static class ReferenceUrls {
        public static final String PERSONAL_WEBSITE = "http://adityakamble49.com";
        public static final String GITHUB_PROFILE = "https://github.com/adityakamble49";
        public static final String TWITTER_PROFILE = "https://twitter.com/adityakamble49";
        public static final String CREDIT_WORD_LIST = "https://github" +
                ".com/maykulkarni/Manhattan-GRE-Words-Shuffled";
        public static final String CREDIT_APP_ICON = "https://www.flaticon" +
                ".com/free-icon/checklist_273504#term=list&page=1&position=96";
    }
}
