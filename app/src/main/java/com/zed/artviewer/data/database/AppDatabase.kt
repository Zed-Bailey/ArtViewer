package com.zed.artviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavouriteArtwork::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artworkDao(): FavouriteArtworkDAO
}