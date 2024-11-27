package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.R
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.presentation.ListPagingFilterMovieCollection
import com.example.movieinfo.presentation.ListPagingMovieCollection
import com.example.movieinfo.presentation.MainViewModel
import com.example.movieinfo.presentation.ui.layout.TabRowDefaults.tabIndicatorOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSettingsMainPageView(viewModel: MainViewModel,navController: NavController) {
    val pageData by lazy { ListPagingFilterMovieCollection.pager(viewModel).flow }
    val mainSearchTabList = listOf("Все", "Фильмы", "Сериалы")
    val mainSortTabList = listOf("Дата", "Популярность", "Рейтинг")
    val yearRange  = viewModel.yearRange.collectAsState()
    val sliderPosition  = viewModel.raitingRange.collectAsState()
    val countrySt by remember { mutableStateOf(viewModel.countriesToId.map { it.second }[viewModel.coutryInd.value]) }
    val genreSt by remember { mutableStateOf(viewModel.genresToId.map { it.second }[viewModel.genreInd.value]) }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = stringResource(R.string.search_settings),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        Text(
            text = stringResource(R.string.view_to),
            color = Color.Black.copy(0.5f),
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp, bottom = 16.dp)
                .align(Alignment.Start)
        )
        SearchTabSection(tabList = mainSearchTabList, tabAddInfo = null, selectedIndex = viewModel.movieTypeInd) {
viewModel.movieTypeInd.intValue = it
        }


        FilterString("Страна", countrySt){
            navController.navigate(MovieInfoDestination.FILTER_COUNTRY)
        }
        FilterString(name = "Жанр", value = genreSt){
navController.navigate(MovieInfoDestination.FILTER_GENRE)
        }
        FilterString(name = "Год", value = "С ${yearRange.value.first} до ${yearRange.value.second}"){
navController.navigate(MovieInfoDestination.FILTER_YEAR)
        }
        FilterString(name = "Рейтинг", value = if (sliderPosition == (0f..10f)) "Любой"
        else "С ${sliderPosition.value.start} до ${sliderPosition.value.endInclusive}"){}
RangeSlider(value = sliderPosition.value,
    steps = 8,
    modifier = Modifier.padding(horizontal = 24.dp),
    onValueChange = {
    range->
        range.start.roundToInt().toFloat()
        range.endInclusive.roundToInt().toFloat()
        viewModel.raitingRange.value = range
},
    valueRange = 1f..10f)
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)) {
        Text(text = sliderPosition.value.start.toInt().toString(), color = Color.Black.copy(0.5f))
        Text(text = sliderPosition.value.endInclusive.toInt().toString(), color = Color.Black.copy(0.5f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.sort),
            color = Color.Black.copy(0.5f),
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp, bottom = 16.dp)
                .align(Alignment.Start)
        )
        SearchTabSection(tabList = mainSortTabList, tabAddInfo = null, selectedIndex = viewModel.sortTypeIndex) {
            viewModel.sortTypeIndex.intValue = it
        }
        Button(onClick = {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.searchByFilter()
            }
        }){
            Text(stringResource(R.string.Search))
        }
        val searchResult = pageData.collectAsLazyPagingItems()
        LazyColumn {
           items(searchResult.itemCount){index->
               searchResult[index]?.let { MovieCardView(it,navController) }
            }
            searchResult.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading->{
                        item { CircularProgressIndicator() }
                    }
                    loadState.refresh is LoadState.Error->{
                        val e= searchResult.loadState.refresh as LoadState.Error
                        item{
                            Column (modifier = Modifier.fillMaxSize()){
                                e.error.localizedMessage?.let {
                                    Text(text =  it)
                                }
                                Button(onClick = {retry()}) { Text("Попробовать снова") }
                            }
                        }
                    }
                    loadState.append is LoadState.Error->{
                        val e = searchResult.loadState.append as LoadState.Error
                        item{
                            Column(modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center) {
                                e.error.localizedMessage?.let {
                                    Text(it)
                                    Button(onClick = {retry()}) {
                                        Text("Попробовать снова")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterString(name: String, value: String,chosen: Boolean = false,rout:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(if (chosen) Color(181, 181, 201) else Color.White)
            .clickable { rout() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, fontSize = 16.sp, modifier = Modifier.padding(start = 36.dp))
        Text(
            text = value, fontSize = 14.sp, color = Color.Black.copy(0.5f),
            modifier = Modifier.padding(end = 36.dp)
        )
    }
}

@Composable
fun SearchTabSection(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    tabAddInfo: List<String>?,
    selectedIndex: MutableIntState = mutableIntStateOf(0),
    onTabSelection: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        selectedIndex
    }
    val tabString = tabList
    val inactiveColor = Color(0xff777777)
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = TabRowDefaults.contentColor,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 0.dp, // Customize the height of the indicator
                    color = Color.Black // Customize the color of the indicator

                )
            }, modifier = modifier.wrapContentSize(align = Alignment.Center)
        ) {
            tabString.forEachIndexed { index, item ->
                when (index) {
                    0 -> {
                        Tab(selected = selectedTabIndex == index,
                            selectedContentColor = Color(61, 59, 255, 1),
                            unselectedContentColor = inactiveColor,
                            modifier = Modifier
                                .padding()
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
                                        ) else Color.White,
                                        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                    )
                                    .border(
                                        1.dp, color = Color.Black,
                                        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (selectedTabIndex == index) Color(
                                            61,
                                            59,
                                            255
                                        ) else Color.Black,
                                        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                    )
                                    .padding(
                                        start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp
                                    )
                            ) {
                                Text(
                                    text = item, fontSize = 16.sp,
                                    color = if (selectedTabIndex == index) Color.White else Color.Black,

                                    )
                                tabAddInfo?.let {
                                    Text(
                                        text = it[index],
                                        fontSize = 14.sp,
                                        color = Color.Black.copy(0.5f),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }

                        }
                    }

                    tabString.lastIndex -> {
                        Tab(selected = selectedTabIndex == index,
                            selectedContentColor = Color(61, 59, 255, 1),
                            unselectedContentColor = inactiveColor,
                            modifier = Modifier
                                .padding()
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
                                        ) else Color.White,
                                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                                    )
                                    .border(
                                        1.dp, color = Color.Black,
                                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (selectedTabIndex == index) Color(
                                            61,
                                            59,
                                            255
                                        ) else Color.Black,
                                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                                    )
                                    .padding(
                                        start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp
                                    )
                            ) {
                                Text(
                                    text = item, fontSize = 16.sp,
                                    color = if (selectedTabIndex == index) Color.White else Color.Black,

                                    )
                                tabAddInfo?.let {
                                    Text(
                                        text = it[index],
                                        fontSize = 14.sp,
                                        color = Color.Black.copy(0.5f),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }

                        }
                    }

                    else -> {
                        Tab(selected = selectedTabIndex == index,
                            selectedContentColor = Color(61, 59, 255, 1),
                            unselectedContentColor = inactiveColor,
                            modifier = Modifier
                                .padding()
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
                                        ) else Color.White,
                                    )
                                    .border(
                                        1.dp,
                                        if (selectedTabIndex == index) Color(
                                            61,
                                            59,
                                            255
                                        ) else Color.Black,
                                    )
                                    .padding(
                                        start = 32.dp, end = 32.dp, top = 4.dp, bottom = 4.dp
                                    )
                            ) {
                                Text(
                                    text = item, fontSize = 16.sp,
                                    color = if (selectedTabIndex == index) Color.White else Color.Black,

                                    )
                                tabAddInfo?.let {
                                    Text(
                                        text = it[index],
                                        fontSize = 14.sp,
                                        color = Color.Black.copy(0.5f),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }

                        }
                    }
                }

            }
        }
    }
}
