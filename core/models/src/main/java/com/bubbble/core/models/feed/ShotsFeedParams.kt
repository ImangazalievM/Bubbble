package com.bubbble.core.models.feed

class ShotsFeedParams(
    val sort: Sorting,
    val page: Int,
    val pageSize: Int
) {

    enum class Sorting {
        POPULAR, RECENT;

        companion object {
            fun find(name: String) = values().firstOrNull {
                it.name == name
            }
        }
    }

}