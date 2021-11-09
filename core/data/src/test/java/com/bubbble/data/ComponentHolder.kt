package com.bubbble.data

import com.bubbble.core.network.Dribbble

object ComponentHolder {

    val component = DaggerParserTestComponent.builder()
        .testModule(TestModule(Dribbble.URL))
        .build()

}