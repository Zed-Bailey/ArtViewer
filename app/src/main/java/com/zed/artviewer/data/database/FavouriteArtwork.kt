package com.zed.artviewer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artworks")
data class FavouriteArtwork(
    @PrimaryKey(autoGenerate = false) val id: String,
    val imageId: String,
    val title: String,
)