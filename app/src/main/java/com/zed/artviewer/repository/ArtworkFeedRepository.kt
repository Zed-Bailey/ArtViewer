package com.zed.artviewer.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zed.artviewer.service.ApiService
import com.zed.artviewer.ui.pagingSource.HomePagingSource
import com.zed.artviewer.ui.pagingSource.SearchPagingSource
import javax.inject.Inject

class ArtworkFeedRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getHomeFeed() = Pager(
        config = PagingConfig(
            pageSize = 12
        ),
        pagingSourceFactory = {
            HomePagingSource(apiService = apiService)
        }
    ).flow

    fun getSearchFeed(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        pagingSourceFactory = {
            SearchPagingSource(apiService = apiService, query = query)
        }
    ).flow
}