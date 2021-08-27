package com.imangazalievm.bubbble.di.global.qualifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Qualifier
@Retention(RUNTIME)
public @interface OkHttpInterceptors {
}
