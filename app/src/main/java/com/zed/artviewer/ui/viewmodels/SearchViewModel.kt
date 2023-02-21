package com.zed.artviewer.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zed.artviewer.data.api.ArtworkGeneral
import com.zed.artviewer.data.api.ArtworkSearch
import com.zed.artviewer.repository.ArtworkFeedRepository
import com.zed.artviewer.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artworkFeedRepository: ArtworkFeedRepository
): ViewModel() {



    fun getSearchResults(query:String): Flow<PagingData<ArtworkSearch>> = artworkFeedRepository.getSearchFeed(query).cachedIn(viewModelScope)


}