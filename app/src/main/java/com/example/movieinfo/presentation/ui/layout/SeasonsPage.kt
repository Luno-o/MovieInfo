package com.example.movieinfo.presentation.ui.layout

import android.widget.SearchView
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.data.moviesDto.SerialWrapperDto
import com.example.movieinfo.entity.Episode
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieForStaff
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.entity.actorCardForPreview
import com.example.movieinfo.entity.dragonEpisode
import com.example.movieinfo.entity.dragonEpisodeList
import com.example.movieinfo.entity.emptyMovieBaseInfo
import com.example.movieinfo.entity.emptyStaffFullInfo
import com.example.movieinfo.entity.filmographyForPreview
import com.example.movieinfo.presentation.MainViewModel
import com.example.movieinfo.presentation.ui.layout.TabRowDefaults.tabIndicatorOffset

@Composable
fun SeasonsPageView(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val serialInfo = viewModel.seasons.collectAsState().value
    val baseInfo = viewModel.movie.collectAsState().value!!
    val tabList = mutableListOf<String>()
    serialInfo.forEachIndexed { index, serialWrapperDto ->
        tabList.add("${index + 1}")
    }
    Column(
        modifier = modifier
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
                    .weight(0.2f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .clickable { navController.popBackStack() }
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = baseInfo.nameRU.toString(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 3

            )
            Box(
                modifier = Modifier
                    .weight(0.2f)
            )
        }
Row(modifier =Modifier.padding(start = 16.dp, bottom = 16.dp),
    verticalAlignment = Alignment.CenterVertically) {
    Text(text = "Сезоны", fontSize = 14.sp)
    SeasonsTabSection (tabList = tabList, tabAddInfo =  null
        ){
        selectedTabIndex = it
    }
}

          LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 8.dp)) {
                serialInfo[selectedTabIndex].episodes.forEachIndexed { index, episode ->
                 item {
                      SeasonItem(episode = episode)
                    }
                }
            }

    }
}
@Composable
fun SeasonsTabSection(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    tabAddInfo: List<String>?,
    onTabSelection: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val tabString = tabList
    val inactiveColor = Color(0xff777777)

    com.example.movieinfo.presentation.ui.layout.ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = TabRowDefaults.contentColor,
        edgePadding = 8.dp,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]).size(0.dp),// Customize the height of the indicator
                color = Color.Black // Customize the color of the indicator

            )
        }, modifier = modifier
    ) {
        tabString.forEachIndexed { index, item ->
            Tab(selected = selectedTabIndex == index,
                selectedContentColor = Color(61, 59, 255, 1),
                unselectedContentColor = inactiveColor,
                modifier = Modifier.padding(start = 8.dp).wrapContentWidth(),
                onClick = {
                    selectedTabIndex = index
                    onTabSelection(index)
                }) {

                Row(verticalAlignment = Alignment.CenterVertically,
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
                ){
                    Text(
                        text = item, fontSize = 16.sp,
                        color = if (selectedTabIndex == index) Color.White else Color.Black,

                        )
                    tabAddInfo?.let { Text(text = it[index], fontSize = 14.sp,color = Color.Black.copy(0.5f)
                        , modifier = Modifier.padding(start = 8.dp)) }
                }

            }

        }
    }
}

@Composable
fun SeasonItem(episode: Episode){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "${episode.episodeNumber} серия. ${episode.nameRu}", fontSize = 14.sp
            )
            Text(text = "${episode.releasedDate}", color = Color.Black.copy(0.5f))
        }
    }
}
