package com.example.movieinfo.presentation.ui.layout

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.R
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.presentation.MainViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.flow.StateFlow

@Parcelize
data class MovieCollectionRow(
    val movieCards: @RawValue StateFlow<List<MovieCollection>>,
    val collectionName: String,
    val movieType: CollectionType
) : Parcelable

@Composable
fun MovieCollectionView(
    viewModel: MainViewModel,
    flow: StateFlow<List<MovieCollection>>,
    collectionName: String,
    movieType: CollectionType,
    navController: NavController,
    modifier: Modifier = Modifier,
    kpID: String = "0",
    allOrCount: Boolean = true,
    showOrDelete: Boolean = true
) {

    val movieCollection = flow.collectAsState().value
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(238.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp),
            text = collectionName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        if (allOrCount) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp)
                    .clickable {
                        navController.navigate("${MovieInfoDestination.COLLECTION_ROUTE}/$movieType/$kpID")
                    },
                text = stringResource(R.string.All),
                fontSize = 14.sp,
                color = Color(61, 59, 255)
            )
        } else {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(32.dp)
                    .padding(end = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate("${MovieInfoDestination.COLLECTION_ROUTE}/$movieType/$kpID")
                    },
                    text = movieCollection.size.toString(),
                    fontSize = 14.sp,
                    color = Color(61, 59, 255)
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_more),
                    contentDescription = null,
                    Modifier
                        .size(18.dp)
                        .padding(3.dp)
                )
            }
        }
        LazyRow(modifier = Modifier.align(Alignment.BottomCenter)) {
            items(movieCollection.size) {
                MovieCardView(movieCard = movieCollection[it], navController)
            }
            if (movieCollection.isNotEmpty()) item {
                if (showOrDelete) {
                    ShowAllCardView(navController, movieType.name, kpID)
                } else {
                    ClearHistoryCardView(viewModel,collectionName)
                }
            }
        }
    }
}

