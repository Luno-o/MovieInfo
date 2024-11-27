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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.R
import com.example.movieinfo.entity.MovieGalleryImpl
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.MovieGallery
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber


@Composable
fun FilmPageView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
    movieId: Int,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var movieIdS by remember { mutableStateOf<Int?>(null) }
    runBlocking {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            if (movieIdS != movieId) {
                movieIdS = movieId
                viewModel.loadMovieById(movieId)
                delay(500)
                viewModel.loadStaffByFilmId(movieId)
                delay(500)
                viewModel.loadMovieGallery(movieId)
                delay(500)
                viewModel.loadSimilarMovie(movieId)
                delay(500)
            }
        }
    }
    val scrollState = rememberScrollState()
    val movieBaseInfo = viewModel.movie
        .collectAsState().value
    val actors = viewModel.staffByFilm
        .collectAsState().value.filter {
            it.professionKey == "ACTOR"

        }
    val staff = viewModel.staffByFilm.collectAsState().value.filter {
        it.professionKey != "ACTOR"
    }
    val movieGallery = viewModel.movieGallery.collectAsState().value
    if (movieBaseInfo !== null) {
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
                    data = movieBaseInfo.coverUrl ?: movieBaseInfo.posterUrl,
                    modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Fit
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
                        .width(192.dp)
                ) {
                    if (movieBaseInfo.logoUrl !== null) {
                        GlideImageWithPreview(
                            data = movieBaseInfo.logoUrl, modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        )
                    }
                    Text(
                        text = "${movieBaseInfo.ratingKinopoisk} ${movieBaseInfo.nameRU}",
                        color = Color.LightGray
                    )
                    Text(text = "${movieBaseInfo.year} ${
                        movieBaseInfo
                            .genreDtos.joinToString { it.genre }
                    }", color = Color.LightGray
                    )
                    val filmLengthHours = "${movieBaseInfo.filmLength?.div(60) ?: 0}"
                    val pluralStringHours = pluralStringResource(
                        id = R.plurals.hours_quantity,
                        count = filmLengthHours.toInt(), filmLengthHours.toInt()
                    )
                    val filmLengthMinutes = "${movieBaseInfo.filmLength?.rem(60) ?: 0}"
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
                var maxLineSD = 2


                Text(
                    text = movieBaseInfo.shortDescription
                        ?: stringResource(R.string.no_short_description),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = overflowSD,
                    maxLines = maxLineSD,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable {
                            if (overflowSD == TextOverflow.Ellipsis) {
                                overflowSD = TextOverflow.Clip
                                maxLineSD = 4
                            } else {
                                overflowSD = TextOverflow.Ellipsis
                                maxLineSD = 2
                            }
                        }
                )
                var overflowFD = TextOverflow.Ellipsis
                val maxLineFD = remember { mutableIntStateOf(5) }
                Text(text = "${movieBaseInfo.description}",
                    maxLines = maxLineFD.intValue, fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis, modifier = Modifier.clickable {
                        if (overflowFD == TextOverflow.Ellipsis) {
                            overflowFD = TextOverflow.Clip
                            maxLineFD.intValue = 10
                        } else {
                            overflowFD = TextOverflow.Ellipsis
                            maxLineFD.intValue = 5
                        }
                    })
            }
            ActorsGridView(staff = actors, navController)
            StaffGridView(staff, navController)
            GalleryView(
                gallery = movieGallery,
                kpID = movieId.toString(),
                navController = navController
            )
            if (viewModel.similarMovies.collectAsState().value.isNotEmpty()) {
                MovieCollectionView(
                    viewModel,
                    viewModel.similarMovies,
                    stringResource(R.string.similar), CollectionType.SIMILAR, navController,
                    modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(), movieBaseInfo.kpID.toString()
                )
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@Composable
fun ToolRow(viewModel: MainViewModel) {
    val kpID = viewModel.movie.collectAsStateWithLifecycle().value?.kpID
    kpID?.let {
        runBlocking {
            Timber.d("ToolRow get collections id by $kpID")
            viewModel.getCollectionsIdForMovie(kpID)
        }
    }
    val collectionsId = viewModel.collectionsIdForMovie.collectAsStateWithLifecycle().value
    val isFav = viewModel.isMyFavourite.collectAsStateWithLifecycle().value
    val isWtchd = viewModel.isMyWatched.collectAsStateWithLifecycle().value
    val isBkmr = viewModel.isMyBookmark.collectAsStateWithLifecycle().value
    Row(
        modifier = Modifier
            .height(34.dp)
            .width(192.dp)
            .padding(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick =
        {
            val favId =
                viewModel.collectionsList.value.find { it.collectionName == "Избранное" }?.id
            favId?.let {
                if (collectionsId.contains(favId)) {
                    viewModel.removeFromMyCollection(favId)
                } else
                    viewModel.addToMyCollection(
                        it
                    )
            }
        }, modifier = Modifier.size(34.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.favourite),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isFav) Color.White else Color.LightGray
            )
        }
        IconButton(
            onClick =
            {
                val bkmrkId =
                    viewModel.collectionsList.value.find { it.collectionName == "Закладки" }?.id
                bkmrkId?.let {
                    if (collectionsId.contains(bkmrkId)) {
                        viewModel.removeFromMyCollection(bkmrkId)

                    } else {
                        viewModel.addToMyCollection(
                            it
                        )
                    }
                }
            },
            modifier = Modifier.size(34.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.mark),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isBkmr) Color.White else Color.LightGray
            )
        }
        IconButton(
            onClick =
            {
                val wtchdId =
                    viewModel.collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
                wtchdId?.let {
                    if (collectionsId.contains(wtchdId)) {
                        viewModel.removeFromMyCollection(wtchdId)
                    } else {
                        viewModel.addToMyCollection(
                            it
                        )
                    }
                }
            },
            modifier = Modifier.size(34.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.watch),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isWtchd) Color.White else Color.LightGray
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(34.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.LightGray
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(34.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.other),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun ActorsGridView(staff: List<Staff>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.movies_had_been_filmed), fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = staff.size.toString(), fontSize = 14.sp, color = Color(61, 59, 255))
                Image(
                    painter = painterResource(id = R.drawable.arrow_more),
                    contentDescription = null,
                    Modifier
                        .size(18.dp)
                        .padding(3.dp)
                )
            }
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(4),
            contentPadding = PaddingValues(0.dp), modifier = Modifier
                .height(300.dp)
                .padding(start = 16.dp)
        ) {
            items(staff.size) {
                ActorCardView(
                    actor = ActorCard(
                        role = staff[it].description, actorName =
                        staff[it].nameRU, phUrl = staff[it].posterUrl
                    ), navController = navController,
                    staffID = staff[it].staffId.toString()
                )
            }
        }
    }
}

@Composable
fun StaffGridView(staff: List<Staff>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.worked_on_film), fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = staff.size.toString(), fontSize = 14.sp, color = Color(61, 59, 255))
                Image(
                    painter = painterResource(id = R.drawable.arrow_more),
                    contentDescription = null,
                    Modifier
                        .size(18.dp)
                        .padding(3.dp)
                )
            }
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.height(150.dp)
        ) {
            items(staff.size) {


                ActorCardView(
                    actor = ActorCard(
                        staff[it].description,
                        staff[it].nameRU, staff[it].posterUrl
                    ), navController = navController,
                    staffID = staff[it].staffId.toString()
                )
            }
        }
    }
}

@Composable
fun GalleryView(gallery: List<MovieGallery>, navController: NavController, kpID: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.gallery), fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, modifier = Modifier.clickable {

                    navController.navigate("${MovieInfoDestination.MOVIE_GALLERY}/$kpID")
                }) {
                Text(text = gallery.size.toString(), fontSize = 14.sp, color = Color(61, 59, 255))
                Image(
                    painter = painterResource(id = R.drawable.arrow_more),
                    contentDescription = null,
                    Modifier
                        .size(18.dp)
                        .padding(3.dp)

                )
            }
        }
        LazyRow(modifier = Modifier.padding(start = 0.dp)) {
            items(gallery.size) {
                GlideImageWithPreview(
                    data = gallery[it].previewUrl,
                    modifier = Modifier
                        .size(192.dp, 108.dp)
                        .padding(end = 28.dp)
                )

            }
        }
    }
}

