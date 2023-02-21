package com.zed.artviewer.ui.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zed.artviewer.data.database.FavouriteArtwork
import com.zed.artviewer.repository.FavouriteArtworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteArtworkRepository: FavouriteArtworkRepository
) : ViewModel() {

    val favourites = favouriteArtworkRepository.allArtworks.asLiveData()


    fun unFavourite(artwork: FavouriteArtwork) {
        viewModelScope.launch {
            favouriteArtworkRepository.deleteArtwork(artwork)
        }
    }
}