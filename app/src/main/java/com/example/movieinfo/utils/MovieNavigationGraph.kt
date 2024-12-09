package com.example.movieinfo.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieType
import timber.log.Timber

@Composable
fun MovieNavigationGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MovieInfoDestination.HOME_ROUTE,
) {
val context = appContainer.context
var startDestinationGraph by remember {  mutableStateOf(startDestination) }
    val vms by lazy {
        SearchPageViewModel.provideFactory(appContainer.searchPageUseCase)
            .create(SearchPageViewModel::class.java)
    }
    val vmc: ShowCollectionViewModel by lazy {
        ShowCollectionViewModel.provideFactory(appContainer.showCollectionUseCase)
            .create(ShowCollectionViewModel::class.java)
    }
    val vmf: FilmPageViewModel by lazy {
        FilmPageViewModel.provideFactory(appContainer.filmPageUseCase)
            .create(FilmPageViewModel::class.java)
    }
    val vma: ActorViewModel by lazy {
        ActorViewModel.provideFactory(appContainer.actorUseCase).create(ActorViewModel::class.java)
    }
    NavHost(
        navController = navController,
        startDestination = startDestinationGraph
    ) {
        composable(MovieInfoDestination.ON_BOARDING) {
            OnboardingView(navController) {startDestinationGraph =  MovieInfoDestination.HOME_ROUTE
            SharedPref.getInstance(context).setIsFirstLaunchToFalse()}
        }

        composable(MovieInfoDestination.HOME_ROUTE) {
            ContainerScreen(navController = navController) {
                val vm: MainPageViewModel = viewModel(
                    factory =
                    MainPageViewModel.provideFactory(appContainer.mainPageUseCase)
                )
                MainPageView(viewModel = vm, navController = navController)
            }
        }
        composable(
            "${MovieInfoDestination.COLLECTION_ROUTE}/{movieType}/{kpID}/{collectionName}",
        ) { navbe ->

            val movieType = navbe.arguments?.getString("movieType")
            val kpID = navbe.arguments?.getString("kpID")
            val collectionName = navbe.arguments?.getString("collectionName")
            Timber.d("movieType = $movieType kpID = $kpID name $collectionName")
            val avm = if (movieType == CollectionType.BEST.name) vma else null
            ContainerScreen(navController = navController) {
                ShowCollectionView(
                    movieType = movieType,
                    collectionName = collectionName,
                    navController = navController,
                    viewModel = vmc,
                    actorViewModel = avm,
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
                    viewModel = vmc
                )
            }
        }
        composable("${MovieInfoDestination.DETAIL_MOVIE}/{kpID}") { navbe ->

            Timber.d("argument ${navbe.arguments?.getString("kpID")}")
            ContainerScreen(navController = navController) {
                FilmPageView(
                    viewModel = vmf,
                    navController = navController,
                    movieId = navbe.arguments
                    !!.getString("kpID")!!.toInt()
                )
            }
        }
        composable("${MovieInfoDestination.DETAIL_SERIAL}/{kpID}") { navbe ->
            ContainerScreen(navController = navController) {
                SerialPageView(
                    viewModel = vmf,
                    navController = navController,
                    movieId = navbe.arguments
                    !!.getString("kpID")!!.toInt()
                )
            }
        }
        composable(MovieInfoDestination.DETAIL_SEASONS) {
            ContainerScreen(navController = navController) {
                SeasonsPageView(vmf, navController)
            }
        }

        composable("${MovieInfoDestination.DETAIL_STAFF}/{staffID}") { navbe ->

            val id = navbe.arguments?.getString("staffID")
            Timber.d("staffID $id")
            if (id !== null)
                ContainerScreen(navController = navController) {
                    ActorPageView(
                        viewModel = vma,
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
                        viewModel = vmf
                    )
                }
        }
        composable(MovieInfoDestination.SEARCH) {
            ContainerScreen(navController = navController, tabIndex = 1) {

                SearchPageView(viewModel = vms, navController = navController)
            }
        }
        composable(MovieInfoDestination.STAFF_FILMOGRAPHY) {
            ContainerScreen(navController = navController, tabIndex = 1) {
                FilmographyPageView(
                    viewModel = vma,
                    navController = navController
                )
            }
        }
        composable(MovieInfoDestination.PROFILE) {
            ContainerScreen(navController = navController, tabIndex = 2) {
                val vm: ProfileViewModel = viewModel(
                    factory =
                    ProfileViewModel.provideFactory(appContainer.profileUseCase)
                )
                ProfilePageView(vm, navController)
            }
        }
        composable(MovieInfoDestination.SEARCH_FILTER) {

            Timber.d("search filter ${vms.hashCode()}")
            SearchSettingsMainPageView(vms, navController)
        }

        composable(MovieInfoDestination.FILTER_COUNTRY) {

            Timber.d("search filter ${vms.hashCode()}")
            SearchFilterPageView(
                MovieInfoDestination.COUNTRY_TYPE_FILTER,
                vms, navController = navController
            )
        }
        composable(MovieInfoDestination.FILTER_GENRE) {

            Timber.d("search filter ${vms.hashCode()}")
            SearchFilterPageView(
                MovieInfoDestination.GENRE_TYPE_FILTER,
                viewModel = vms, navController = navController
            )
        }
        composable(MovieInfoDestination.FILTER_YEAR) {

            Timber.d("search filter ${vms.hashCode()}")
            PeriodPageView(vms, navController)
        }
    }
}