package com.bubbble.tests.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun Any.launchCo(
        block: suspend CoroutineScope.() -> Unit
) = GlobalScope.launch(Dispatchers.Default) { block() }