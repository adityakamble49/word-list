package com.adityakamble49.wordlist.di.module

import com.adityakamble49.wordlist.ui.main.MainActivity
import com.adityakamble49.wordlist.ui.related.RelatedWordsActivity
import com.adityakamble49.wordlist.ui.word.WordActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * UI Module
 *
 * @author Aditya Kamble
 * @since 30/9/2018
 */
@Module
abstract class UIModule {

    @ContributesAndroidInjector()
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [RelatedWordsFragmentModule::class])
    abstract fun contributesRelatedWordsActivity(): RelatedWordsActivity

    @ContributesAndroidInjector
    abstract fun contributesWordActivity(): WordActivity
}