package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.SearchPageViewModel
import com.example.movieinfo.utils.MovieInfoDestination

@Composable
fun SearchFilterPageView(
    filterType: Int = 0,
    viewModel: SearchPageViewModel,
    navController: NavController
) {
    var pageName: String? = null
    var list: List<String>? = null
    var state: Array<Int>? = null
    var placeholder: String? = null
    val scrollState = rememberScrollState()
    var isCountry = false

    when (filterType) {
        MovieInfoDestination.COUNTRY_TYPE_FILTER -> {
            pageName = stringResource(R.string.country)
            list = viewModel.countriesToId.map { it.second }
            if (viewModel.queryCountry.text.isNotEmpty()) {
                list = list.filter { it.contains(viewModel.queryCountry.text, true) }
            }
            state = viewModel.filterMovie.value.countryInd
            placeholder = stringResource(R.string.enter_country)
            isCountry = true
        }

        MovieInfoDestination.GENRE_TYPE_FILTER -> {
            pageName = stringResource(R.string.genre)
            list = viewModel.genresToId.map { it.second }
            if (viewModel.queryGenre.text.isNotEmpty()) {
                list = list.filter { it.contains(viewModel.queryGenre.text, true) }
            }
            state = viewModel.filterMovie.value.genreInd
            placeholder = stringResource(R.string.enter_genre)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                    .clickable { navController.popBackStack() }
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = pageName!!,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        SearchAppBarCountry(viewModel, placeholder, isCountry)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            list?.forEachIndexed { index, s ->
                FilterString(name = list[index], chosen = state?.first() == index, value = "") {
                    state = arrayOf(index)
                    if (isCountry) {
                        viewModel.filterMovie.value.countryInd = state!!
                    } else {
                        viewModel.filterMovie.value.genreInd = state!!
                    }
                    navController.navigate(MovieInfoDestination.SEARCH_FILTER)
                }
            }
        }
    }
}

@Composable
fun SearchAppBarCountry(viewModel: SearchPageViewModel, placeholder: String?, isCountry: Boolean) {
    SearchAppBarFilter(
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = Color.Black.copy(0.4f),
                contentDescription = "Search Icon",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        trailingIcon = null,
        modifier = Modifier
            .background(
                Color(181, 181, 201, 0x88)
            )
            .padding(4.dp)
            .height(32.dp),
        fontSize = 10.sp,
        placeholderText = placeholder ?: "", viewModel = viewModel, isCountry = isCountry
    )
}

@Composable
fun SearchAppBarFilter(
    modifier: Modifier = Modifier, viewModel: SearchPageViewModel,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    isCountry: Boolean = true,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize

) {
    // Immediately update and keep track of query from text field changes.

    val query = if (isCountry) viewModel.queryCountry else viewModel.queryGenre
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
    }
}