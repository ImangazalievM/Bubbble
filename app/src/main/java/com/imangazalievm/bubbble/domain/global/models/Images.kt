package com.imangazalievm.bubbble.domain.global.models

class Images(
   private val hidpi: String?,
   private val normal: String?,
   private val teaser: String
) {

    fun best(): String {
        return hidpi ?: (normal ?: teaser)
    }

}