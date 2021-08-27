package com.imangazalievm.bubbble.di.global.qualifiers

import kotlin.annotation.MustBeDocumented
import kotlin.annotation.Retention
import kotlin.annotation.AnnotationRetention
import javax.inject.Qualifier

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpNetworkInterceptors