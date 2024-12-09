package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.ActorViewModel
import com.example.movieinfo.utils.MovieInfoDestination
import com.movieinfo.domain.entity.CollectionType
import kotlinx.coroutines.runBlocking


@Composable
fun ActorPageView(
    viewModel: ActorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier, staffID: String,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val scrollState = rememberScrollState()
    val staffFullInfo = viewModel.staff.collectAsState()
    runBlocking {
        viewModel.loadStaffById(staffID.toInt())
    }
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(scrollState)
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
        }
        Row {
            GlideImageWithPreview(
                data = staffFullInfo.value.posterUrl, modifier = Modifier
                    .size(146.dp, 201.dp)
                    .padding(start = 16.dp)
            )
            Column {
                staffFullInfo.value.nameRU?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                staffFullInfo.value.profession?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color.Black.copy(0.5f),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        MovieCollectionView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            viewModel = null,
            flow = viewModel.staffMovieCollection,
            collectionName = stringResource(R.string.best),
            movieType = CollectionType.BEST,
            navController = navController,
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .clickable {
                    navController.navigate(MovieInfoDestination.STAFF_FILMOGRAPHY)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Фильмография", fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${staffFullInfo.value.films.size} фильма",
                    color = Color.Black.copy(0.5f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "К списку", fontSize = 14.sp, color = Color(61, 59, 255))
                Image(
                    painter = painterResource(id = R.drawable.arrow_more),
                    contentDescription = null,
                    Modifier
                        .size(18.dp)
                        .padding(3.dp)

                )
            }
        }
    }
}
