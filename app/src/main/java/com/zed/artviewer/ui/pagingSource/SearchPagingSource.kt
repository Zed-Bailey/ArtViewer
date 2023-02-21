package com.zed.artviewer.ui.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zed.artviewer.data.api.ArtworkGeneral
import com.zed.artviewer.data.api.ArtworkSearch
import com.zed.artviewer.service.ApiService


// https://proandroiddev.com/pagination-in-jetpack-compose-with-and-without-paging-3-e45473a352f4
class SearchPagingSource(
    private val query: String,
    private val apiService: ApiService
): PagingSource<Int, ArtworkSearch>() {

    override fun getRefreshKey(state: PagingState<Int, ArtworkSearch>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtworkSearch> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchArtworks(query = query, page = page)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.data.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}