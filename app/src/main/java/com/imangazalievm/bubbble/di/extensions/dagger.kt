package com.imangazalievm.bubbble.di.extensions

import com.imangazalievm.bubbble.BubbbleApplication
import com.imangazalievm.bubbble.di.ApplicationComponent

val appComponent: ApplicationComponent
    get() = BubbbleApplication.component