package com.zed.artviewer.data.api

data class ArtResponse<T> (
    val config: Config,
    val data: T,
    val info: Info,
    val pagination: Pagination
)