package com.adityakamble49.wordlist.ui.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Base Activity
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
    }

    abstract fun bindView()
}