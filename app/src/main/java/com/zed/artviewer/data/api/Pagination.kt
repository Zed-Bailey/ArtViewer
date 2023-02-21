package com.zed.artviewer.data.api

data class Pagination(
    val current_page: Int,
    val limit: Int,
    val next_url: String,
    val offset: Int,
    val total: Int,
    val total_pages: Int
)