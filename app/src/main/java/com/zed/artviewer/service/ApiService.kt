package com.zed.artviewer.service

import com.zed.artviewer.data.api.ArtResponse
import com.zed.artviewer.data.api.ArtworkDetail
import com.zed.artviewer.data.api.ArtworkGeneral
import com.zed.artviewer.data.api.ArtworkSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("artworks?fields=id,image_id,title,medium_display,artist_title")
    suspend fun getArtworks(@Query("page") page: Int = 1) : ArtResponse<List<ArtworkGeneral>>

    @GET("artworks/search")
    suspend fun searchArtworks(@Query("q") query: String, @Query("page") page: Int): ArtResponse<List<ArtworkSearch>>

    @GET("artworks/{id}")
    suspend fun getArtworkById(@Path("id") id: String): ArtResponse<ArtworkDetail>

}