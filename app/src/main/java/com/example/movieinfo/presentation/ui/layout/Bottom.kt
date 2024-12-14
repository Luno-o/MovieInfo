package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieinfo.utils.MovieInfoDestination
import com.example.movieinfo.R
import com.example.movieinfo.utils.NavigationActions


@Composable
fun ContainerScreen(
    navController: NavController,
    tabIndex: Int = 0,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
) {
    val actions = NavigationActions(navController)
    ContainerScreenContent(
        bottomBarContent = {
            BottomTabSection(tabIndex = tabIndex) {
                when (it) {
                    0 -> {
                        navController.navigate(MovieInfoDestination.HOME_ROUTE)
                    }

                    1 -> {
                        navController.navigate(MovieInfoDestination.SEARCH)
                    }

                    2 -> {
                        navController.navigate(MovieInfoDestination.PROFILE)
                    }
                }
            }
        },
        content = {
            content(it)
        },
    )
}


@Composable
private fun ContainerScreenContent(
    bottomBarContent: @Composable () -> Unit = {},
    content: @Composable (innerPadding: PaddingValues) -> Unit = {},
) {
    Scaffold(
        bottomBar = bottomBarContent
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun BottomTabSection(
    modifier: Modifier = Modifier,
    tabIndex: Int = 0,
    onTabSelection: (selectedIndex: Int) -> Unit,
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(tabIndex)
    }
    val tabDrawable = listOf(
        R.drawable.home_icon,
        R.drawable.search_icon, R.drawable.profile_icon
    )
    val inactiveColor = Color(0xff777777)
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = TabRowDefaults.primaryContentColor,
            divider = {},
            indicator = { tabPositions ->
                androidx.compose.material3.TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 0.dp, // Customize the height of the indicator
                    color = Color.Black // Customize the color of the indicator

                )
            }, modifier = modifier
                .height(64.dp)
                .background(
                    shape = CutCornerShape(topStart = 4.dp, topEnd = 4.dp),
                    color = Color.White
                )
                .shadow(
                    elevation = 16.dp,
                    shape = CutCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    ambientColor = Color.Blue
                )
        ) {

            tabDrawable.forEachIndexed { index, item ->
                Tab(selected = selectedTabIndex == index,
                    selectedContentColor = Color(61, 59, 255, 1),
                    unselectedContentColor = inactiveColor,
                    modifier = Modifier,
                    onClick = {
                        selectedTabIndex = index
                        onTabSelection(index)
                    }) {

                    Icon(
                        painter = painterResource(item),
                        contentDescription = null,
                        tint = if (selectedTabIndex == index) Color(
                            61,
                            59,
                            255
                        ) else Color.Gray
                    )


                }
            }

        }
    }
}


