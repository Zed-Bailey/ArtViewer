package com.zed.artviewer.data

object ArticConstants {
    fun createImageUrl(imageId: String): String {
        return "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
    }
}