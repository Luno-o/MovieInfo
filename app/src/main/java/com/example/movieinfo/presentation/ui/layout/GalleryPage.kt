package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.primaryContentColor
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.FilmPageViewModel
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieGallery
import kotlinx.coroutines.runBlocking

@Composable
fun GalleryPageView(
    kpID: String,
    viewModel: FilmPageViewModel,
    navController: NavController,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var list = emptyList<MovieGallery>()
    val listOfGallery = viewModel.movieGalleryAll.collectAsState().value.toMap()
    runBlocking {
//        viewModel.loadMovieGallery(kpID.toInt())
        viewModel.loadMovieGalleryAll(kpID.toInt())
    }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val tabList = viewModel.tabListGallery

    val additionalInfo = listOfGallery.values.map { it.size.toString() }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null,
                Modifier
                    .size(16.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .clickable { navController.popBackStack() }
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = stringResource(id = R.string.gallery),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        GalleryTabSection(tabList = tabList, tabAddInfo = additionalInfo) {
            selectedTabIndex = it
        }
        when (selectedTabIndex) {
            GalleryType.SHOOTING.ordinal -> listOfGallery[GalleryType.SHOOTING]?.let { list = it }
            GalleryType.COVER.ordinal -> listOfGallery[GalleryType.COVER]?.let { list = it }
            GalleryType.PROMO.ordinal -> listOfGallery[GalleryType.PROMO]?.let { list = it }
            GalleryType.FAN_ART.ordinal -> listOfGallery[GalleryType.FAN_ART]?.let { list = it }
            GalleryType.WALLPAPER.ordinal -> listOfGallery[GalleryType.WALLPAPER]?.let { list = it }
            GalleryType.CONCEPT.ordinal -> listOfGallery[GalleryType.CONCEPT]?.let { list = it }
            GalleryType.POSTER.ordinal -> listOfGallery[GalleryType.POSTER]?.let { list = it }
            GalleryType.SCREENSHOT.ordinal -> listOfGallery[GalleryType.SCREENSHOT]?.let {
                list = it
            }

            GalleryType.STILL.ordinal -> listOfGallery[GalleryType.STILL]?.let { list = it }
            else -> {}
        }
        LazyVerticalGrid(
            columns = object : GridCells {
                override fun Density.calculateCrossAxisCellSizes(
                    availableSize: Int,
                    spacing: Int
                ): List<Int> {
                    val firstColumn = (availableSize - spacing) * 1 / 2
                    val secondColumn = availableSize - spacing - firstColumn
                    return listOf(firstColumn, secondColumn)
                }
            },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            if (list.isNotEmpty())
                list.forEachIndexed { index, item ->
                    if (index % 3 == 0) {
                        item(span = { GridItemSpan(2) }) {
                            GlideImageWithPreview(
                                data = item.imageUrl, modifier = Modifier
                                    .size(308.dp, 173.dp)
                            )
                        }
                    } else {
                        item(span = { GridItemSpan(1) }) {
                            GlideImageWithPreview(
                                data = item.imageUrl,
                                modifier = Modifier.size(146.dp, 82.dp)
                            )
                        }
                    }
                }
        }
    }
}

@Composable
fun GalleryTabSection(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    tabAddInfo: List<String>?,
    onTabSelection: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val inactiveColor = Color(0xff777777)

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = primaryContentColor,
        edgePadding = 8.dp,
        divider = {},
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 0.dp, // Customize the height of the indicator
                color = Color.Black // Customize the color of the indicator
            )
        }, modifier = modifier
    ) {
        tabList.forEachIndexed { index, item ->
            Tab(selected = selectedTabIndex == index,
                selectedContentColor = Color(61, 59, 255, 1),
                unselectedContentColor = inactiveColor,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .wrapContentWidth(),
                onClick = {
                    selectedTabIndex = index
                    onTabSelection(index)
                }) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            if (selectedTabIndex == index) Color(
                                61,
                                59,
                                255
                            ) else Color.White, shape = RoundedCornerShape(15.dp)
                        )
                        .border(
                            1.dp,
                            if (selectedTabIndex == index) Color(61, 59, 255) else Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                ) {
                    Text(
                        text = item, fontSize = 16.sp,
                        color = if (selectedTabIndex == index) Color.White else Color.Black,

                        )
                    tabAddInfo?.let {
                        if (tabAddInfo.isNotEmpty())
                            Text(
                                text = it[index],
                                fontSize = 14.sp,
                                color = if (selectedTabIndex == index) Color.White else Color.Black.copy(
                                    0.5f
                                ), modifier = Modifier.padding(start = 8.dp)
                            )
                    }
                }

            }

        }
    }
}
