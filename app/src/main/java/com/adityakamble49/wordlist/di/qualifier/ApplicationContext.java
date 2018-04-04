package com.adityakamble49.wordlist.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Application Context Qualifier
 *
 * @author Aditya Kamble
 * @since 4/4/2018
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
