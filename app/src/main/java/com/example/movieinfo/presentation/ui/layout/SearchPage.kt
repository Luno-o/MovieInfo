package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfo.R
import com.movieinfo.domain.entity.MovieBaseInfo
import com.example.movieinfo.presentation.ui.viewModels.SearchPageViewModel
import com.example.movieinfo.utils.MovieInfoDestination
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.entity.MovieCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SearchPageView(
    modifier: Modifier = Modifier,
    viewModel: SearchPageViewModel,
    navController: NavController
) {


    val movies: LazyPagingItems<MovieCollection> =
        viewModel.movieSearchResult.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchAppBarMovie(viewModel, navController = navController)
        Timber.d("movies: ${movies.itemCount}")
        if (movies.itemCount > 0) {
            LazyColumn(modifier.padding(horizontal = 16.dp)) {
                items(movies.itemCount) {
                    movies[it]?.let { it1 ->
                        FilmographyMovieItem(movieCard = movies[it],navController)
                    }
                }
                movies.apply {
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
                            val e = movies.loadState.refresh as LoadState.Error
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
                            val e = movies.loadState.append as LoadState.Error
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
            }
        } else Text(
            text = stringResource(R.string.not_found),
            fontSize = 16.sp, modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchAppBarMovie(viewModel: SearchPageViewModel, navController: NavController) {
    SearchAppBar(
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = Color.Black.copy(0.4f),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable {
                    }
            )
        },
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.trailing_icon),
                    tint = Color.Black.copy(0.5f),
                    contentDescription = "Clear Icon",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            navController.navigate(MovieInfoDestination.SEARCH_FILTER)
                        }
                )
            }

        },
        modifier = Modifier
            .background(
                Color(181, 181, 201, 0x88)
            )
            .padding(4.dp)
            .height(32.dp),
        fontSize = 10.sp,
        placeholderText = stringResource(R.string.hint_search_query), viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier, viewModel: SearchPageViewModel,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize

) {
    // Immediately update and keep track of query from text field changes.
    val query = viewModel.queryState
    var showClearIcon by rememberSaveable { mutableStateOf(false) }


    if (query.text.isEmpty()) {
        showClearIcon = false
    } else if (query.text.isNotEmpty()) {
        showClearIcon = true
    }
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(CircleShape)
            .wrapContentSize()
    )
    {
        BasicTextField(query,
            modifier = Modifier
                .fillMaxWidth(),
            lineLimits = androidx.compose.foundation.text.input.TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()

                    Box(Modifier.weight(1f)) {
                        if (query.text.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    fontSize = fontSize
                                ),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }

            })
//BasicTextField(modifier = Modifier
//    .fillMaxWidth()
//    , value = query,
//    onValueChange ={ onQueryChanged ->
//    // If user makes changes to text, immediately updated it.
//    query = onQueryChanged
//    // To avoid crash, only query when string isn't empty.
//    if (onQueryChanged.isNotEmpty()) {
//        // Pass latest query to refresh search results.
//
//    }
//},
//    singleLine = true,
//    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
//    decorationBox = {innerTextField ->
//        Row(
//            modifier,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            if (leadingIcon != null) leadingIcon()
//
//            Box(Modifier.weight(1f)) {
//                if (query.isEmpty()) {
//                    Text(
//                        text = placeholderText,
//                        style = LocalTextStyle.current.copy(
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
//                            fontSize = fontSize
//                        ),
//                        modifier = Modifier
//                            .padding(start = 4.dp)
//                            .align(Alignment.CenterStart)
//                    )
//                }
//                innerTextField()
//            }
//            if (trailingIcon != null) trailingIcon()
//        }
//
//    }
        //    )
    }
//    TextField(
//        value = query,
//        onValueChange = { onQueryChanged ->
//            // If user makes changes to text, immediately updated it.
//            query = onQueryChanged
//            // To avoid crash, only query when string isn't empty.
//            if (onQueryChanged.isNotEmpty()) {
//                // Pass latest query to refresh search results.
////                viewModel.performQuery(onQueryChanged)
//            }
//        },
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Rounded.Search,
//                tint = Color.Black.copy(0.5f),
//                contentDescription = "Search Icon"
//            )
//        },
//        trailingIcon = {
//                IconButton(onClick = { query = "" }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.trailing_icon),
//                        tint = Color.Black.copy(0.5f),
//                        contentDescription = "Clear Icon",
//                        modifier = Modifier.padding(end = 16.dp)
//                    )
//                }
//
//        },
//        maxLines = 1,
//        colors = TextFieldDefaults.textFieldColors(containerColor = Color(181,181,201)),
//        placeholder = { Text(text = stringResource(R.string.hint_search_query)
//            , color = Color.Black.copy(0.5f), fontSize = 14.sp)},
//        modifier = Modifier
//            .fillMaxWidth()
//
//,
//
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//    )

}