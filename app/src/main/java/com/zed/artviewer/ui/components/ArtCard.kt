package com.zed.artviewer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zed.artviewer.data.ArticConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtCard(imageId: String?, title: String, favourite: Boolean, onClick: () -> Unit, onFavourite: () -> Unit) {

    val imageUrl = if(imageId != null) {
        ArticConstants.createImageUrl(imageId)
    } else {
        "https://placeholder.com/200x400?text=No+Image+Available"
    }


    var favouriteState by remember {
        mutableStateOf(favourite)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onClick()
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedImageLoader(url = imageUrl)

            Spacer(Modifier.height(10.dp))
            Text(title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleMedium)

        }

        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            IconButton(onClick = {
                favouriteState = !favouriteState
                onFavourite()
            }) {
                if(favourite) {
                    Icon(Icons.Filled.Favorite, "favourite button", tint = Color.Red)
                } else {
                    Icon(Icons.Rounded.Favorite, "favourite button")
                }

            }
        }
    }
}

@Preview
@Composable
fun ArtCardPreview() {
    ArtCard(
        imageId = "230e8e8e-7726-b793-2a4a-05223efb221a",
        title = "A Sunday on La Grande Jatte — 1884",
        favourite = false,
        onClick = {},
        onFavourite = {}
    )
}