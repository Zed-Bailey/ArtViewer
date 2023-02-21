package com.zed.artviewer.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zed.artviewer.data.api.ArtworkDetail
import com.zed.artviewer.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    var data by mutableStateOf<ArtworkDetail?>(null)
    var loading by mutableStateOf(false)

    var imageBasePath by mutableStateOf("")

    fun getDetail(id: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = apiService.getArtworkById(id)
                imageBasePath = response.config.iiif_url
                data = response.data
            } catch(e: Exception) {
                Log.e("com.zed.artviewer", e.message.toString())
            } finally {
                loading = false
            }
        }
    }

}