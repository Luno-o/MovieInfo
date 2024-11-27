package com.example.movieinfo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.presentation.MainViewModel
import com.example.movieinfo.presentation.ui.layout.ActorPageView
import com.example.movieinfo.presentation.ui.layout.FilmPageView
import com.example.movieinfo.presentation.ui.layout.FilmographyPageView
import com.example.movieinfo.presentation.ui.layout.GalleryPageView
import com.example.movieinfo.presentation.ui.layout.MainPageView
import com.example.movieinfo.presentation.ui.layout.PeriodPageView
import com.example.movieinfo.presentation.ui.layout.ProfilePageView
import com.example.movieinfo.presentation.ui.layout.SearchFilterPageView
import com.example.movieinfo.presentation.ui.layout.SearchPageView
import com.example.movieinfo.presentation.ui.layout.SearchSettingsMainPageView
import com.example.movieinfo.presentation.ui.layout.SeasonsPageView
import com.example.movieinfo.presentation.ui.layout.SerialPageView
import com.example.movieinfo.presentation.ui.layout.ShowCollectionView
import com.example.movieinfo.presentation.ui.layout.ContainerScreen
import timber.log.Timber

@Composable
fun MovieNavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MovieInfoDestination.HOME_ROUTE,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MovieInfoDestination.HOME_ROUTE) {
            ContainerScreen(navController = navController) {
                MainPageView(viewModel = viewModel, navController = navController)
            }
        }
        composable(
            "${MovieInfoDestination.COLLECTION_ROUTE}/{movieType}/{kpID}",
        ) { navbe ->
            val movieType = navbe.arguments?.getString("movieType")
            val kpID = navbe.arguments?.getString("kpID")
            Timber.d("movieType = $movieType kpID = $kpID")
            ContainerScreen(navController = navController) {
                ShowCollectionView(
                    movieType = movieType,
                    navController = navController,
                    viewModel = viewModel,
                    kpID = kpID ?: "0"
                )
            }
        }
        composable(
            "${MovieInfoDestination.COLLECTION_ROUTE}/{collectionName}",
        ) { navbe ->
            val collectionName = navbe.arguments?.getString("collectionName")
            Timber.d("launch by collection name $collectionName")
            ContainerScreen(navController = navController, tabIndex = 2) {
                ShowCollectionView(
                    collectionName = collectionName,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
        composable("${MovieInfoDestination.DETAIL_MOVIE}/{kpID}") { navbe ->
            Timber.d("argument ${navbe.arguments?.getString("kpID")}")
            ContainerScreen(navController = navController) {
                FilmPageView(
                    viewModel = viewModel,
                    navController = navController,
                    movieId = navbe.arguments
                    !!.getString("kpID")!!.toInt()
                )
            }
        }
        composable("${MovieInfoDestination.DETAIL_SERIAL}/{kpID}") { navbe ->
            Timber.d("argument ${navbe.arguments?.getString("kpID")}")
            ContainerScreen(navController = navController) {
                SerialPageView(
                    viewModel = viewModel,
                    navController = navController,
                    movieId = navbe.arguments
                    !!.getString("kpID")!!.toInt()
                )
            }
        }
        composable(MovieInfoDestination.DETAIL_SEASONS) {
            ContainerScreen(navController = navController) {
                SeasonsPageView(viewModel, navController)
            }
        }

        composable("${MovieInfoDestination.DETAIL_STAFF}/{staffID}") { navbe ->
            val id = navbe.arguments?.getString("staffID")
            Timber.d("staffID $id")
            if (id !== null)
                ContainerScreen(navController = navController) {
                    ActorPageView(
                        viewModel = viewModel,
                        navController = navController, staffID = id
                    )
                }
        }
        composable("${MovieInfoDestination.MOVIE_GALLERY}/{kpID}") { navbe ->
            val id = navbe.arguments?.getString("kpID")
            if (id !== null)
                ContainerScreen(navController = navController) {
                    GalleryPageView(
                        id,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
        }
        composable(MovieInfoDestination.SEARCH) {
            ContainerScreen(navController = navController, tabIndex = 1) {
                SearchPageView(viewModel = viewModel, navController = navController)
            }
        }
        composable(MovieInfoDestination.STAFF_FILMOGRAPHY) {
            ContainerScreen(navController = navController, tabIndex = 1) {
                FilmographyPageView(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable(MovieInfoDestination.PROFILE) {
            ContainerScreen(navController = navController, tabIndex = 2) {
                ProfilePageView(viewModel, navController)
            }
        }
        composable(MovieInfoDestination.SEARCH_FILTER) {
            SearchSettingsMainPageView(viewModel, navController)
        }

        composable(MovieInfoDestination.FILTER_COUNTRY) {
            SearchFilterPageView(
                MovieInfoDestination.COUNTRY_TYPE_FILTER,
                viewModel, navController = navController
            )
        }
        composable(MovieInfoDestination.FILTER_GENRE) {
            SearchFilterPageView(
                MovieInfoDestination.GENRE_TYPE_FILTER,
                viewModel = viewModel, navController = navController
            )
        }
        composable(MovieInfoDestination.FILTER_YEAR) {
            PeriodPageView(viewModel, navController)
        }
    }
}