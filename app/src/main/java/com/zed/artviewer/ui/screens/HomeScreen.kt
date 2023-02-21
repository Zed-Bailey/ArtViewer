package com.zed.artviewer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zed.artviewer.R
import com.zed.artviewer.data.api.ArtworkGeneral
import com.zed.artviewer.data.ArticConstants
import com.zed.artviewer.data.database.FavouriteArtwork
import com.zed.artviewer.ui.components.AnimatedImageLoader
import com.zed.artviewer.ui.components.ArtCard
import com.zed.artviewer.ui.components.LottieAnimatedView
import com.zed.artviewer.ui.navigation.Screen
import com.zed.artviewer.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val artworks = viewModel.getArtworks().collectAsLazyPagingItems()
    val favIds = viewModel.favouriteArtworkIds

    LaunchedEffect(Unit) {
        viewModel.getArtworks()
    }

    Box() {

        LazyColumn(

        ){
            items(
                items = artworks,
                key = { it.id }
            ) {
                it?.let {
                    ArtCard (
                        imageId = it.image_id,
                        title = it.title,
                        favourite = favIds.value?.contains(it.id) ?: false,
                        onClick = {
                            navController.navigate(Screen.DetailScreen.route + "/${it.id}")
                        },
                        onFavourite = {
                            viewModel.favourite(FavouriteArtwork(it.id, it.image_id ?: "", it.title))
                        }
                    )
                }

            }

            when(val state = artworks.loadState.refresh) {
                is LoadState.Error -> {
                    item {
                        Text(state.error.message.toString(), color = MaterialTheme.colorScheme.error)
                    }
                }
                is LoadState.Loading -> {
                    item {
                        LottieAnimatedView(resId = R.raw.lottie_painting)
                    }
                }
                else -> {}
            }

            when(val state = artworks.loadState.append) {
                is LoadState.Error ->  {
                    item {
                        Text(state.error.message.toString(), color = MaterialTheme.colorScheme.error)
                    }
                }
                is LoadState.Loading ->{
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Loading")

                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                else -> {}
            }

        }

    }

}


