package com.zed.artviewer.ui.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")

    object SearchScreen : Screen("search_screen")

    object FavouriteScreen : Screen("favourite_screen")

    object DetailScreen : Screen("detail_screen")
}
