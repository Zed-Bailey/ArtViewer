package com.zed.artviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zed.artviewer.ui.navigation.Screen
import com.zed.artviewer.ui.screens.DetailScreen
import com.zed.artviewer.ui.screens.FavouriteScreen
import com.zed.artviewer.ui.screens.HomeScreen
import com.zed.artviewer.ui.screens.SearchScreen
import com.zed.artviewer.ui.theme.ArtViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtViewerTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()

                var canPop by remember {
                    mutableStateOf(false)
                }
                var destination by remember {
                    mutableStateOf("")
                }

                navController.addOnDestinationChangedListener { controller,  _, _ ->
                    canPop = controller.previousBackStackEntry != null
                    destination = controller.currentDestination?.route ?: ""
                    Log.i("com.zed.artview", "curr route = ${controller.currentDestination?.route}")
                }

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        CenterAlignedTopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = {
                                Text("Art Viewer")
                            },
                            navigationIcon = {
                                // only show the back button on the detail screen
                                if (
                                    destination.contains(Screen.DetailScreen.route)
                                ) {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(Icons.Default.ArrowBack, "navigate back")
                                    }
                                }

                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar() {
                            val currentRoute = backStackEntry.value?.destination?.route

                            NavigationBarItem(
                                selected = Screen.HomeScreen.route == currentRoute,
                                onClick = {
                                    navController.navigate(Screen.HomeScreen.route)
                                },
                                label = {
                                    Text("Home")
                                },
                                icon = {
                                    Icon(Icons.Rounded.Home, "Home screen")
                                }
                            )

                            NavigationBarItem(
                                selected = Screen.SearchScreen.route == currentRoute,
                                onClick = {
                                    navController.navigate(Screen.SearchScreen.route)
                                },
                                label = {
                                    Text("Search")
                                },
                                icon = {
                                    Icon(Icons.Rounded.Search, "Search screen")
                                }
                            )

                            NavigationBarItem(
                                selected = Screen.FavouriteScreen.route == currentRoute,
                                onClick = {
                                    navController.navigate(Screen.FavouriteScreen.route)
                                },
                                label = {
                                    Text("Favourites")
                                },
                                icon = {
                                    Icon(Icons.Rounded.Favorite, "Favourite screen")
                                }
                            )
                        }
                    }
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            ),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        SetupNavHost(navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

        composable(Screen.FavouriteScreen.route) {
            FavouriteScreen(navController = navController)
        }

        composable(Screen.DetailScreen.route + "/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                nullable = false
            }
        )) {
            DetailScreen(id = it.arguments?.getString("id")!!)
        }

    }
}