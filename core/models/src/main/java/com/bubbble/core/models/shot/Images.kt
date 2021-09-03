package com.bubbble.core.models.shot

class Images(
   private val hidpi: String?,
   private val normal: String?,
   private val teaser: String
) {

    fun best(): String {
        return hidpi ?: (normal ?: teaser)
    }

}