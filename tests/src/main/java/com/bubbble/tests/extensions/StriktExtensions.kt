package com.bubbble.tests.extensions

import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun <T> T.assertEquals(value: T?) {
    expectThat(this).isEqualTo(value)
}
