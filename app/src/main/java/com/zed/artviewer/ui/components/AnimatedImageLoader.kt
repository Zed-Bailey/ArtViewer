package com.zed.artviewer.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valentinilk.shimmer.shimmer
import kotlin.math.min

@Composable
fun AnimatedImageLoader(url: String, contentDescription: String? = null) {

    // animation is used from here
//    https://www.sinasamaki.com/loading-images-using-coil-in-jetpack-compose/

    var loading by remember {
        mutableStateOf(false)
    }

    var success by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf(false)
    }
    Log.d("com.zed.artviewer.AnimatedImageLoader", url)

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build(),
        onState = {
            // update mutable values on state changes
            when (it) {
                is AsyncImagePainter.State.Loading -> loading = true
                is AsyncImagePainter.State.Success -> {
                    success = true
                    loading = false
                }
                else -> {

                    error = true
                    loading = false
                }

            }
        },
        contentScale = ContentScale.FillBounds
    )

    val state = painter.state

    val transition by animateFloatAsState(
        targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
    )

    if (loading) {

        Box(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .shimmer()
                .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))

        ) { }

    } else if (success) {

        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .scale(.8f + (.2f * transition))
                .graphicsLayer { rotationX = (1f - transition) * 5f }
                .alpha(min(1f, transition / .2f)),
        )

    } else if (error) {


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))

        ) {
            Text("Error loading image", textAlign = TextAlign.Center)
        }

    }

}