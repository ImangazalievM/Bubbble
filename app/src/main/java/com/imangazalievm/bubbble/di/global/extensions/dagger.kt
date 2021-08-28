package com.imangazalievm.bubbble.di.global.extensions

import com.imangazalievm.bubbble.BubbbleApplication
import com.imangazalievm.bubbble.di.global.ApplicationComponent

val appComponent: ApplicationComponent
    get() = BubbbleApplication.component