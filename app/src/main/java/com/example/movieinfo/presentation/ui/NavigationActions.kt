package com.example.movieinfo.presentation.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.movieinfo.MovieInfoDestination

class NavigationActions(navController: NavController) {
    val navigateToHome:()-> Unit = {
        navController.navigate(MovieInfoDestination.HOME_ROUTE){
            popUpTo ( navController.graph.findStartDestination().id ){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}