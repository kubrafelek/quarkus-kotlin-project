package org.example.web

import jakarta.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.FUNCTION

@Qualifier
@Retention(RUNTIME)
@Target(FUNCTION, FIELD)
annotation class NoJsonRootWrap