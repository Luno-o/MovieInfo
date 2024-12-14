package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.FilmPageViewModel
import com.movieinfo.domain.entity.CollectionType
import com.example.movieinfo.utils.MovieInfoDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun SerialPageView(
    modifier: Modifier = Modifier, viewModel: FilmPageViewModel = viewModel(),
    navController: NavController, movieId: Int, innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var movieIdS by remember { mutableStateOf<Int?>(null) }
    val movieBaseInfo = viewModel.movie.collectAsState().value
    val scrollState = rememberScrollState()
    val listSerialWrapperDto = viewModel.seasons
        .collectAsState().value
    val actors = viewModel.staffByFilm.collectAsState().value.filter {
        it.professionKey == "ACTOR"

    }
    val staff = viewModel.staffByFilm.collectAsState().value.filter {
        it.professionKey != "ACTOR"
    }

    val movieGallery = viewModel.movieGallery.collectAsState().value
    runBlocking {
        if (movieIdS != movieId) {
            movieIdS = movieId
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.loadMovieById(movieId)
                delay(200)
                viewModel.loadSeasons(movieId)
                delay(200)
                viewModel.loadStaffByFilmId(movieId)
                delay(200)
                viewModel.loadMovieGallery(movieId)
                delay(200)
                viewModel.loadSimilarMovie(movieId)
            }
        }
    }
    if (movieBaseInfo != null) {
        Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GlideImageWithPreview(
                    data = movieBaseInfo.coverUrl,
                    modifier = Modifier.fillMaxWidth()
                )
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(232.dp)
                ) {
                    if (movieBaseInfo.logoUrl !== null) {
                        GlideImageWithPreview(
                            data = movieBaseInfo.logoUrl, modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)

                        )
                    }
                    Text(
                        text = "${movieBaseInfo.ratingKinopoisk} ${movieBaseInfo.nameRU}",
                        color = Color.LightGray
                    )
                    Text(text = "${movieBaseInfo.year} ${
                        movieBaseInfo
                            .genres.joinToString { it.genre }
                    }", color = Color.LightGray
                    )
                    val filmLengthHours = movieBaseInfo.filmLength?.div(60)?.toString() ?: "0"
                    val pluralStringHours = pluralStringResource(
                        id = R.plurals.hours_quantity,
                        count = filmLengthHours.toInt(), filmLengthHours.toInt()
                    )
                    val filmLengthMinutes = movieBaseInfo.filmLength?.rem(60)?.toString() ?: "0"
                    val pluralStringMinutes = pluralStringResource(
                        id = R.plurals.minutes_quantity,
                        count = filmLengthMinutes.toInt(), filmLengthMinutes.toInt()
                    )
                    Text(text = "${
                        movieBaseInfo.countries.joinToString
                        { it.country }
                    }, $pluralStringHours $pluralStringMinutes, " +
                            "${movieBaseInfo.ratingAgeLimits?.filter { it.isDigit() }}+",
                        color = Color.LightGray
                    )
                    ToolRow(viewModel)
                }
            }
            Column(
                modifier = Modifier.padding(
                    top = 32.dp,
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                var overflowSD = TextOverflow.Ellipsis
                val maxLineSD = remember { mutableIntStateOf(2) }
                Text(
                    text = "${movieBaseInfo.shortDescription}",
                    fontSize = 16.sp,
                    maxLines = maxLineSD.value,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable {
                            if (overflowSD == TextOverflow.Ellipsis) {
                                overflowSD = TextOverflow.Clip
                                maxLineSD.intValue = 4
                            } else {
                                overflowSD = TextOverflow.Ellipsis
                                maxLineSD.intValue = 2
                            }
                        }
                )
                var overflowFD = TextOverflow.Ellipsis
                val maxLineFD = remember { mutableIntStateOf(5) }
                Text(text = "${movieBaseInfo.description}",
                    maxLines = maxLineFD.value, fontSize = 16.sp, modifier = Modifier.clickable {
                        if (overflowFD == TextOverflow.Ellipsis) {
                            overflowFD = TextOverflow.Clip
                            maxLineFD.intValue = 10
                        } else {
                            overflowFD = TextOverflow.Ellipsis
                            maxLineFD.intValue = 5
                        }
                    })
            }
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
                        text = "Сезоны и серии", fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    val seasons = listSerialWrapperDto.size
                    var series = 0
                    listSerialWrapperDto.forEachIndexed { index, serialWrapperDto ->
                        series += serialWrapperDto.episodes
                            .filter { !it.releasedDate.isNullOrEmpty() }
                            .size
                    }
                    Text(text = "$seasons сезон, $series серий", color = Color.Black.copy(0.5f))
                }
                Text(modifier = modifier.clickable {
                    navController.navigate(MovieInfoDestination.DETAIL_SEASONS)
                }, text = "Все", fontSize = 14.sp, color = Color(61, 59, 255))
            }
            ActorsGridView(staff = actors, navController)
            StaffGridView(staff, navController)
            GalleryView(
                gallery = movieGallery, navController = navController,
                kpID = movieBaseInfo.kpID.toString()
            )
            if (viewModel.similarMovies.collectAsState().value.isNotEmpty()) {
                MovieCollectionView(
                    modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    null,
                    viewModel.similarMovies,
                    stringResource(id = R.string.similar), CollectionType.SIMILAR, navController,
                    movieId.toString()
                )
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}



