package com.zed.artviewer.data.api

data class ArtworkGeneral (
    val id: String,
    val image_id: String?,
    val title: String,
    val medium_display: String,
    val artist_title: String?
)