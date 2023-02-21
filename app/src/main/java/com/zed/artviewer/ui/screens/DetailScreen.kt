package com.zed.artviewer.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.zed.artviewer.R
import com.zed.artviewer.data.ArticConstants
import com.zed.artviewer.ui.components.AnimatedImageLoader
import com.zed.artviewer.ui.components.LottieAnimatedView
import com.zed.artviewer.ui.viewmodels.DetailViewModel
import de.mr_pine.zoomables.ZoomableImage
import de.mr_pine.zoomables.ZoomableState
import de.mr_pine.zoomables.rememberZoomableState

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val loading = viewModel.loading
    val model = viewModel.data

    var imageId by remember {
        mutableStateOf<String?>(null)
    }
    var imageZoomed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.getDetail(id)
    }

    if(loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LottieAnimatedView(resId = R.raw.lottie_painting)
        }
    } else {
       if(imageZoomed) {
            Column (
                Modifier
                    .fillMaxSize()
            ) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    IconButton(onClick = {
                        imageZoomed = false
                    }) {
                        Icon(Icons.Rounded.Close, "close image button")
                    }
                }

                ImageDetail(url = ArticConstants.createImageUrl(imageId!!))

            }
       } else {

           Column(
               Modifier
                   .fillMaxSize()
                   .verticalScroll(rememberScrollState())
                   .padding(15.dp)
           ) {
               model?.let { artwork ->

                   imageId = artwork.image_id

                   Column(horizontalAlignment = CenterHorizontally) {
                       if(imageId != null) {
                           Box(
                               modifier = Modifier.clickable {
                                   imageZoomed = true
                               }
                           ) {


                               AnimatedImageLoader(
                                   url = ArticConstants.createImageUrl(
                                       artwork.image_id!!
                                   ),
                                   contentDescription = artwork.thumbnail.alt_text
                               )

                               Icon(
                                   Icons.Rounded.ZoomIn,
                                   null,
                                   modifier = Modifier.align(BottomEnd)
                               )

                           }
                       }


                       Spacer(Modifier.height(15.dp))

                       Text(
                           artwork.title,
                           style = MaterialTheme.typography.headlineSmall,
                           textAlign = TextAlign.Center,
                           modifier = Modifier
                               .fillMaxWidth()
                       )
                   }


                   SectionDisplay(header = "Date", body = artwork.date_display)

                   SectionDisplay(header = "Artist", body = artwork.artist_display)

                   SectionDisplay(header = "Artistic Medium", body = artwork.medium_display)

                   SectionDisplay(header = "Exhibition history", body = artwork.exhibition_history ?: "No known Exhibition History")

                   SectionDisplay(header = "Collection History", body = artwork.provenance_text ?: "No known collection history")

               }
           }
       }
    }

}


@Composable
fun SectionDisplay(header: String, body: String) {
    Text(header, style = MaterialTheme.typography.headlineMedium)
    Divider()
    Text(body, style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.height(10.dp))
}

@Composable
fun ImageDetail(url: String) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )


    ZoomableImage(
        modifier = Modifier.fillMaxSize(),
        coroutineScope = rememberCoroutineScope(),
        zoomableState = rememberZoomableState(
            rotationBehavior = ZoomableState.Rotation.DISABLED
        ),
        painter = painter,
        dragGesturesEnabled = {
            !notTransformed
        }
    )

}