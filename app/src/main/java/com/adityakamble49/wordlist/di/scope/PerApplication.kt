package com.adityakamble49.wordlist.di.scope

import javax.inject.Scope

/**
 * Dagger scope for per application
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication
