package com.bubbble.core.models.shot

class ShotsParams(
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