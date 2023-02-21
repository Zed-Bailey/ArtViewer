package com.zed.artviewer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zed.artviewer.R
import com.zed.artviewer.data.ArticConstants
import com.zed.artviewer.ui.components.LottieAnimatedView
import com.zed.artviewer.ui.navigation.Screen
import com.zed.artviewer.ui.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {


    var queryText by remember {
        mutableStateOf("")
    }

    var searchClicked by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = queryText,
                onValueChange = { queryText = it },
                placeholder = {
                    Text("Search Artwork")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // dismisses keyboard
                        focusManager.clearFocus()
                        searchClicked = true
                    }
                )
            )

            Spacer(Modifier.width(5.dp))

            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    searchClicked = true
                }
            ) {
                Icon(Icons.Rounded.Search, null)
            }
        }


        if (searchClicked) {
            val results = viewModel.getSearchResults(queryText).collectAsLazyPagingItems()

            LazyColumn(

            ) {
                items(
                    items = results,
                    key = { it.id }
                ) {
                    it?.let {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clickable {
                                    navController.navigate(Screen.DetailScreen.route + "/${it.id}")
                                }
                        ) {
                            Box() {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        it.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Icon(
                                    Icons.Rounded.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.align(
                                        CenterEnd
                                    )
                                )

                            }
                        }
                    }

                }

                when (val state = results.loadState.refresh) {
                    is LoadState.Error -> {
                        item {
                            Text(
                                state.error.message.toString(),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    is LoadState.Loading -> {
                        item {
                            LottieAnimatedView(resId = R.raw.lottie_painting)
                        }
                    }
                    else -> {}
                }

                when (val state = results.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            Text(
                                state.error.message.toString(),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    is LoadState.Loading -> {
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
}