package com.example.movieinfo.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.movieinfo.presentation.ui.layout.ActorPageView
import com.example.movieinfo.presentation.ui.viewModels.MainPageViewModel
import com.example.movieinfo.presentation.ui.layout.ContainerScreen
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
import com.example.movieinfo.presentation.ui.layout.onboarding.OnboardingView
import com.example.movieinfo.presentation.ui.viewModels.ActorViewModel
import com.example.movieinfo.presentation.ui.viewModels.FilmPageViewModel
import com.example.movieinfo.presentation.ui.viewModels.ProfileViewModel
import com.example.movieinfo.presentation.ui.viewModels.SearchPageViewModel
import com.example.movieinfo.presentation.ui.viewModels.ShowCollectionViewModel
import timber.log.Timber

@Composable
fun MovieNavigationGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MovieInfoDestination.HOME_ROUTE,
) {
    val context = appContainer.context
    var startDestinationGraph by remember { mutableStateOf(startDestination) }


    NavHost(
        navController = navController,
        startDestination = startDestinationGraph
    ) {
        composable(MovieInfoDestination.ON_BOARDING) {
            OnboardingView(navController) {
                startDestinationGraph = MovieInfoDestination.HOME_ROUTE
                SharedPref.getInstance(context).setIsFirstLaunchToFalse()
            }
        }

        composable(MovieInfoDestination.HOME_ROUTE) {
            ContainerScreen(navController = navController) {
                val viewModel = hiltViewModel<MainPageViewModel>()
                MainPageView(viewModel = viewModel, navController = navController)
            }
        }
        composable(
            "${MovieInfoDestination.COLLECTION_ROUTE}/{movieType}/{kpID}/{collectionName}",
        ) { navbe ->
            val movieType = navbe.arguments?.getString("movieType")
            val kpID = navbe.arguments?.getString("kpID")
            val collectionName = navbe.arguments?.getString("collectionName")
            val actorViewModel = hiltViewModel<ActorViewModel>()
            val viewModel = hiltViewModel<ShowCollectionViewModel>()
            Timber.d("movieType = $movieType kpID = $kpID name $collectionName")
            ContainerScreen(navController = navController) {
                ShowCollectionView(
                    viewModel = viewModel,
                    actorViewModel = actorViewModel,
                    movieType = movieType,
                    collectionName = collectionName,
                    navController = navController,
                    kpID = kpID ?: "0"
                )
            }
        }
        composable(
            "${MovieInfoDestination.COLLECTION_ROUTE}/{collectionName}",
        ) { navbe ->
            val collectionName = navbe.arguments?.getString("collectionName")
            Timber.d("launch by collection name $collectionName")
            val viewModel = hiltViewModel<ShowCollectionViewModel>()
            ContainerScreen(navController = navController, tabIndex = 2) {
                ShowCollectionView(
                    viewModel = viewModel,
                    collectionName = collectionName,
                    navController = navController,
                )
            }
        }
        navigation(
            startDestination = MovieInfoDestination.DETAIL_MOVIE,
            route = MovieInfoDestination.DETAIL_MOVIE_PARENT
        ) {
            composable("${MovieInfoDestination.DETAIL_MOVIE}/{kpID}") { navbe ->
                val parentEntry = remember(navbe) {
                    navController.getBackStackEntry(MovieInfoDestination.DETAIL_MOVIE_PARENT)
                }
                Timber.d("argument ${navbe.arguments?.getString("kpID")}")
                val viewModel = hiltViewModel<FilmPageViewModel>(parentEntry)
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
                ContainerScreen(navController = navController) {
                    val parentEntry = remember(navbe) {
                        navController.getBackStackEntry(MovieInfoDestination.DETAIL_MOVIE_PARENT)
                    }
                    val viewModel = hiltViewModel<FilmPageViewModel>(parentEntry)
                    SerialPageView(
                        viewModel = viewModel,
                        navController = navController,
                        movieId = navbe.arguments
                        !!.getString("kpID")!!.toInt()
                    )
                }
            }
            composable(MovieInfoDestination.DETAIL_SEASONS) {navbe->
                val parentEntry = remember(navbe) {
                    navController.getBackStackEntry(MovieInfoDestination.DETAIL_MOVIE_PARENT)
                }
                val viewModel = hiltViewModel<FilmPageViewModel>(parentEntry)
                ContainerScreen(navController = navController) {
                    SeasonsPageView(viewModel = viewModel, navController = navController)
                }
            }

            composable("${MovieInfoDestination.MOVIE_GALLERY}/{kpID}") { navbe ->
                val parentEntry = remember(navbe) {
                    navController.getBackStackEntry(MovieInfoDestination.DETAIL_MOVIE_PARENT)
                }
                val id = navbe.arguments?.getString("kpID")
                val viewModel = hiltViewModel<FilmPageViewModel>(parentEntry)
                if (id !== null)
                    ContainerScreen(navController = navController) {
                        GalleryPageView(
                            viewModel = viewModel,
                            kpID = id,
                            navController = navController
                        )
                    }
            }
        }
        navigation(
            startDestination = MovieInfoDestination.DETAIL_STAFF,
            route = MovieInfoDestination.DETAIL_STAFF_PARENT
        ) {
            composable("${MovieInfoDestination.DETAIL_STAFF}/{staffID}") { navbe ->
                val parentEntry = remember(navbe) {
                    navController.getBackStackEntry(MovieInfoDestination.DETAIL_STAFF_PARENT)
                }
                val id = navbe.arguments?.getString("staffID")
                Timber.d("staffID $id")
                val viewModel = hiltViewModel<ActorViewModel>(parentEntry)
                if (id !== null)
                    ContainerScreen(navController = navController) {
                        ActorPageView(
                            viewModel = viewModel, navController = navController, staffID = id
                        )
                    }
            }

            composable(MovieInfoDestination.STAFF_FILMOGRAPHY) {
                ContainerScreen(navController = navController, tabIndex = 1) {navbe->
                    val parentEntry = remember(navbe) {
                        navController.getBackStackEntry(MovieInfoDestination.DETAIL_STAFF_PARENT)
                    }
                    val viewModel = hiltViewModel<ActorViewModel>(parentEntry)
                    FilmographyPageView(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
        composable(MovieInfoDestination.PROFILE) {
            ContainerScreen(navController = navController, tabIndex = 2) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                ProfilePageView(viewModel = viewModel, navController = navController)
            }
        }

        navigation(
            startDestination = MovieInfoDestination.SEARCH,
            route = MovieInfoDestination.SEARCH_PARENT
        ) {
            composable(MovieInfoDestination.SEARCH) { be ->
                val parentEntry = remember(be) {
                    navController.getBackStackEntry(MovieInfoDestination.SEARCH_PARENT)
                }
                ContainerScreen(navController = navController, tabIndex = 1) {
                    val viewModel = hiltViewModel<SearchPageViewModel>(parentEntry)
                    SearchPageView(viewModel = viewModel, navController = navController)
                }
            }
            composable(MovieInfoDestination.SEARCH_FILTER) { be ->
                val parentEntry = remember(be) {
                    navController.getBackStackEntry(MovieInfoDestination.SEARCH_PARENT)
                }
                val viewModel = hiltViewModel<SearchPageViewModel>(parentEntry)
                SearchSettingsMainPageView(viewModel = viewModel, navController = navController)
            }

            composable(MovieInfoDestination.FILTER_COUNTRY) { be ->
                val parentEntry = remember(be) {
                    navController.getBackStackEntry(MovieInfoDestination.SEARCH_PARENT)
                }
                val viewModel = hiltViewModel<SearchPageViewModel>(parentEntry)
                SearchFilterPageView(
                    MovieInfoDestination.COUNTRY_TYPE_FILTER,
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(MovieInfoDestination.FILTER_GENRE) { be ->
                val parentEntry = remember(be) {
                    navController.getBackStackEntry(MovieInfoDestination.SEARCH_PARENT)
                }
                val viewModel = hiltViewModel<SearchPageViewModel>(parentEntry)
                SearchFilterPageView(
                    MovieInfoDestination.GENRE_TYPE_FILTER,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(MovieInfoDestination.FILTER_YEAR) { be ->
                val parentEntry = remember(be) {
                    navController.getBackStackEntry(MovieInfoDestination.SEARCH_PARENT)
                }
                val viewModel = hiltViewModel<SearchPageViewModel>(parentEntry)
                PeriodPageView(viewModel = viewModel, navController = navController)
            }
        }
    }
}