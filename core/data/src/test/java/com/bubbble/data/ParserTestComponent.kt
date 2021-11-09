package com.bubbble.data

import com.bubbble.core.network.di.BaseApiUrl
import com.bubbble.data.global.parsing.PageParserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class])
interface ParserTestComponent {

    fun pageParserManager(): PageParserManager

    @BaseApiUrl
    fun baseUrl(): String

}