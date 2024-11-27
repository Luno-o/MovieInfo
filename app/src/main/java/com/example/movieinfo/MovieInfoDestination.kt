package com.example.movieinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object MovieInfoDestination {
    const val HOME_ROUTE = "mainPage"
    const val COLLECTION_ROUTE = "collections"
    const val DETAIL_MOVIE = "detailMovie"
    const val DETAIL_SERIAL = "detailSerial"
    const val DETAIL_SEASONS = "detailSeasons"
    const val DETAIL_STAFF = "detailStaff"
    const val MOVIE_GALLERY = "movieGallery"
    const val STAFF_FILMOGRAPHY = "staffFilmography"
    const val SEARCH = "search"
    const val SEARCH_FILTER = "searchfilter"
    const val FILTER_COUNTRY = "searchcountry"
    const val FILTER_GENRE = "searchgenre"
    const val FILTER_YEAR = "searchyear"
    const val PROFILE = "profile"
    const val COUNTRY_TYPE_FILTER = 0
    const val GENRE_TYPE_FILTER = 1

}
class MovieInfoNavigationActions(navController: NavController){
    val navigateMainPage:()->Unit= {
        navController.navigate(MovieInfoDestination.HOME_ROUTE){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }
    val navigateToCollection : ()-> Unit = {
        navController.navigate(MovieInfoDestination.COLLECTION_ROUTE){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}