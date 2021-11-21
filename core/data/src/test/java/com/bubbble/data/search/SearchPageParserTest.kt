package com.bubbble.data.search

import com.bubbble.core.network.Dribbble
import com.bubbble.data.shots.search.SearchPageParser
import com.bubbble.tests.extensions.readTextFile
import com.google.gson.Gson
import io.kotest.core.spec.style.DescribeSpec

class SearchPageParserTest : DescribeSpec({

    val searchPageParser = SearchPageParser(Gson())

    describe("shots getting") {
        context("shots") {
            val htmlText = readTextFile("html-responses/shots-list.html")
            val shots = searchPageParser.parseHtml(htmlText, Dribbble.URL,)
            //val parseManager = ComponentHolder.component.pageParserManager()
            //parseManager.parse(searchPageParser, SearchParams(
            //    page = 1,
            //    pageSize = 10,
            //    searchQuery = "app",
            //    searchType = SearchType.SHOT,
            //    sort = ShotSortType.POPULAR
            //))
            //val dow = PageDownloader(searchPageParser.getUrl())
            //searchPageParser.parseHtml(shotsHtml)
        }
    }

})