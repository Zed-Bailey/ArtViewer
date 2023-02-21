package com.zed.artviewer.repository

import androidx.annotation.WorkerThread
import com.zed.artviewer.data.database.FavouriteArtwork
import com.zed.artviewer.data.database.FavouriteArtworkDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavouriteArtworkRepository @Inject constructor(
    private val dao: FavouriteArtworkDAO
) {
    val allArtworks: Flow<List<FavouriteArtwork>> = dao.getAllFavourites()



    fun getAllFavouriteArtworkIds() : Flow<List<String>> {
        return dao.getAllFavouriteIds()
    }

    suspend fun deleteArtwork(artwork: FavouriteArtwork) {
        dao.deleteArtwork(artwork)
    }

    suspend fun insertArtwork(artwork: FavouriteArtwork) {
        dao.insertArtwork(artwork)
    }
}