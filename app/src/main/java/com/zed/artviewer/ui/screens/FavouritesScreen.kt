package com.zed.artviewer.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zed.artviewer.ui.components.ArtCard
import com.zed.artviewer.ui.navigation.Screen
import com.zed.artviewer.ui.viewmodels.FavouriteViewModel

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val favourites = viewModel.favourites



    LazyColumn() {
        favourites.value?.let {  art ->
            items(art.size) {
                ArtCard(
                    imageId = art[it].imageId,
                    title = art[it].title,
                    true,
                    onClick = {
                        navController.navigate(Screen.DetailScreen.route + "/${art[it].imageId}")
                    },
                    onFavourite = {
                        viewModel.unFavourite(art[it])
                    }
                )
            }
        }
    }

}