package com.zed.artviewer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteArtworkDAO {

    @Query("SELECT * FROM artworks")
    fun getAllFavourites(): Flow<List<FavouriteArtwork>>

    @Query("SELECT id FROM artworks")
    fun getAllFavouriteIds(): Flow<List<String>>

    @Insert(onConflict = REPLACE)
    suspend fun insertArtwork(artwork: FavouriteArtwork)

    @Delete
    suspend fun deleteArtwork(artwork: FavouriteArtwork)
}