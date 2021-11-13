package com.bubbble.core.models.feed

class ShotsFeedParams(
    val sort: Sorting?
) {

    enum class Sorting {
        PERSONAL, POPULAR, RECENT;

        companion object {
            fun find(name: String) = values().firstOrNull {
                it.name == name
            }
        }
    }

}