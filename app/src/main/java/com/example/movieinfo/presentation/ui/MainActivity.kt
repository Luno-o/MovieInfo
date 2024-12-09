package com.example.movieinfo.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movieinfo.App
import com.example.movieinfo.presentation.ui.layout.onboarding.LoadingScreen
import com.example.movieinfo.utils.ConnectivityObserver
import com.example.movieinfo.utils.MovieInfoDestination
import com.example.movieinfo.utils.MovieNavigationGraph
import com.example.movieinfo.utils.NetworkConnectivityObserver
import com.example.movieinfo.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

lateinit var start: String
    private val connectivityObserver by lazy { NetworkConnectivityObserver(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as App).container

        setContent {
            Surface {
                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Unavailable
                )
                if (status == ConnectivityObserver.Status.Available) {

                    start = if (isFirstLaunch()){
                        MovieInfoDestination.ON_BOARDING
                    } else MovieInfoDestination.HOME_ROUTE
                    val navController = rememberNavController()
                    MovieNavigationGraph(
                        navController = navController,
                        appContainer = appContainer, startDestination = start
                    )
                } else {
                    LoadingScreen()
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(text = "Network status : $status")
//                    }
                }

            }
        }
    }
private fun isFirstLaunch() : Boolean{
    return SharedPref.getInstance(application).isFirstLaunch()
}
}

