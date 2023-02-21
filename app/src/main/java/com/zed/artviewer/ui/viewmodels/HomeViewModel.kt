package com.zed.artviewer.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zed.artviewer.data.api.ArtworkDetail
import com.zed.artviewer.data.api.ArtworkGeneral
import com.zed.artviewer.data.database.FavouriteArtwork
import com.zed.artviewer.repository.ArtworkFeedRepository
import com.zed.artviewer.repository.FavouriteArtworkRepository
import com.zed.artviewer.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val artworkFeedRepository: ArtworkFeedRepository,
    private val repo : FavouriteArtworkRepository
): ViewModel() {

    fun getArtworks(): Flow<PagingData<ArtworkGeneral>> = artworkFeedRepository.getHomeFeed().cachedIn(viewModelScope)

    val favouriteArtworkIds = repo.getAllFavouriteArtworkIds().asLiveData()


    fun favourite(artwork: FavouriteArtwork) {
        viewModelScope.launch {
            if(favouriteArtworkIds.value?.contains(artwork.id) == true) {
                repo.deleteArtwork(artwork)
            } else {
                repo.insertArtwork(artwork)
            }
        }
    }
}