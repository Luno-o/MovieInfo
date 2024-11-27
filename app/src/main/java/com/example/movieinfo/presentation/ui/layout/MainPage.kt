package com.example.movieinfo.presentation.ui.layout

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.MovieType
import com.example.movieinfo.presentation.MainViewModel


@Composable
fun MainPageView(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {

    val collections = viewModel.setCollections()


    Column(modifier.background(Color.White)) {
        Text(
            text = stringResource(R.string.appname),
            fontSize = 18.sp, modifier = Modifier
                .padding(start = 16.dp, top = 24.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp), contentPadding = innerPadding,
            state = state
        ) {
            items(collections.size) {
                MovieCollectionView(
                    viewModel,
                    collections[it].movieCards,
                    collections[it].collectionName,
                    movieType = collections[it].movieType,
                    navController = navController
                )


            }
            item { Spacer(
                Modifier
                    .wrapContentWidth()
                    .height(64.dp)) }
        }

    }
}
