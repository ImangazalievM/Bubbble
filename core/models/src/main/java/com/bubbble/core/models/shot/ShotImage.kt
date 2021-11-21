package com.bubbble.core.models.shot

class ShotImage(
    val imageUrl: String,
    val size: Size
) {
    class Size(val width: Int, val height: Int)
}