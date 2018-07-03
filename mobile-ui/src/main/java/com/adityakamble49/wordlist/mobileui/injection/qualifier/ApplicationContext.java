package com.adityakamble49.wordlist.mobileui.injection.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Application Context Qualifier
 *
 * @author Aditya Kamble
 * @since 3/7/2018
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
