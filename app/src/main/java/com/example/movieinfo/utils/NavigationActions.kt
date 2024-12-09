package com.example.movieinfo.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NavigationActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(MovieInfoDestination.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProfile: () -> Unit = {
        navController.navigate(MovieInfoDestination.PROFILE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSearch: () -> Unit = {
        navController.navigate(MovieInfoDestination.SEARCH) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}