package com.example.movieinfo.presentation.ui.layout


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.ActorViewModel
import com.movieinfo.domain.entity.CollectionType
import com.example.movieinfo.presentation.ui.viewModels.ShowCollectionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber


@Composable
fun ShowCollectionView(
    modifier: Modifier = Modifier,
    movieType: String? = null,
    navController: NavController,
    viewModel: ShowCollectionViewModel,
    actorViewModel: ActorViewModel? = null,
    innerPadding: PaddingValues =
        PaddingValues(0.dp),
    kpID: String = "0",
    collectionName: String? = null
) {
    var collectionNameState by remember { mutableStateOf<String?>(null) }
    var collectionRow: MovieCollectionRow? = null
    val pageData by lazy {
        viewModel.getPagingData(movieType, kpID)
    }

    when (movieType) {
        CollectionType.WATCHED.name -> {
            collectionRow = collectionName?.let {
                MovieCollectionRow(
                    viewModel.yourCollection,
                    it, CollectionType.WATCHED
                )
            }

        }
        CollectionType.BEST.name -> {
            collectionRow = collectionName?.let {cn->
                actorViewModel?.let { avm->
                MovieCollectionRow(
                    avm.staffMovieCollection,
                    cn, CollectionType.BEST
                )
                }
            }

        }

        CollectionType.INTEREST.name -> {
            collectionRow = MovieCollectionRow(
                viewModel.yourCollection,
                stringResource(R.string.by_ur_interest), CollectionType.INTEREST
            )

        }

        null -> {
            runBlocking {
                if (collectionNameState !== collectionName) {

                    collectionNameState = collectionName

                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Timber.d("launch getCollection $collectionName $collectionNameState")
                        if (collectionName != null) {
                            viewModel.getCollectionByNameFlow(collectionName)
                        }
                    }
                }
            }
            if (collectionName !== null) {
                collectionRow = MovieCollectionRow(
                    viewModel.yourCollection,
                    collectionName, CollectionType.MY_COLLECTIONS
                )
            }
        }

        else -> {

        }
    }
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null,
                Modifier
                    .size(16.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .clickable {
                        navController.popBackStack()
                    })

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = collectionName ?: "",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        if (collectionRow?.movieType == CollectionType.MY_COLLECTIONS
            || collectionRow?.movieType == CollectionType.INTEREST
            || collectionRow?.movieType == CollectionType.WATCHED
            || collectionRow?.movieType == CollectionType.BEST

        ) {
            val collection = collectionRow.movieCards.collectAsState().value

            runBlocking {
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    if (collectionNameState !== collectionName) {

                        collectionNameState = collectionName
                        if (collectionName != null) {
                            viewModel.getCollectionsList()
                            viewModel.getCollectionByNameFlow(collectionName)
                        }
                    }
                }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 56.dp, end = 56.dp),
                content = {
                    try {
                        items(collection.size) {
                            MovieCardView(
                                movieCard = collectionRow.movieCards.value[it],
                                navController
                            )
                        }
                    } catch (e: Exception) {

                        Timber.d(" ошибка ${e.message}")
                    }
                })
        } else {

            val items = pageData?.collectAsLazyPagingItems()
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 56.dp, end = 56.dp),
                content = {
                    if (items != null) {
                        items(items.itemCount) {
                            items[it]?.let { it1 -> MovieCardView(movieCard = it1, navController) }
                        }
                    }
                    items?.apply {
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

                            loadState.append is LoadState.Loading -> {
                                item { CircularProgressIndicator() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val e = items.loadState.refresh as LoadState.Error
                                item {
                                    Column(modifier = modifier.fillMaxSize()) {
                                        e.error.localizedMessage?.let {
                                            Text(text = it)
                                        }
                                        Button(onClick = { retry() }) { Text("Попробовать снова") }
                                    }
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                val e = items.loadState.append as LoadState.Error
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        e.error.localizedMessage?.let {
                                            Text(it)
                                            Button(onClick = { retry() }) {
                                                Text("Попробовать снова")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
        }

    }

}
