package com.zed.artviewer.data.api

data class SuggestAutocompleteAll(
    val contexts: Contexts,
    val input: List<String>,
    val weight: Int
)