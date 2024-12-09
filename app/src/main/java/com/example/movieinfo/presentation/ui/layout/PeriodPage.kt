package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.SearchPageViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar

@Composable
fun PeriodPageView(viewModel: SearchPageViewModel, navController: NavController) {
    val period = viewModel.yearRange
    val years = (1998..Calendar.getInstance().get(Calendar.YEAR)).toList()
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
                text = stringResource(R.string.period),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        Text(
            text = stringResource(R.string.search_from),
            color = Color.Black.copy(0.5f),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.Start)
        )

        PagerForPick(
            list = years, state = viewModel.yearAfter, periodType = 0,
            availableYear = viewModel.yearBefore.intValue
        )
        Text(
            text = stringResource(R.string.search_until),
            color = Color.Black.copy(0.5f),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, top = 16.dp)
        )
        PagerForPick(
            list = years, state = viewModel.yearBefore,
            periodType = 1, availableYear = viewModel.yearAfter.intValue
        )
        Button(
            onClick = {
                period.value = Pair(
                    viewModel.yearAfter.intValue,
                    viewModel.yearBefore.intValue
                )
                viewModel.filterMovie.value = viewModel.filterMovie.value.copy(
                    yearBefore = viewModel.yearBefore.intValue,
                    yearAfter = viewModel.yearAfter.intValue
                )
                navController.popBackStack()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(61, 59, 255)
            ), modifier = Modifier.padding(top = 64.dp)
        ) {
            Text(text = "Выбрать", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerForPick(
    list: List<Int>, state: MutableIntState, periodType: Int = 0,
    availableYear: Int
) {
    val chunkedList = list.chunked(12)
    val pagerState = rememberPagerState {
        chunkedList.size
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .border(
                1.dp, Color.Black,
                RoundedCornerShape(12.dp)
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${list.first()} - ${list.last()}", textAlign = TextAlign.Center,
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(61, 59, 255)
            )
            Row(modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            if ((pagerState.currentPage - 1) >= 0)
                                runBlocking {
                                    launch {
                                        pagerState.scrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(180f)
                        .scale(0.9f)
                        .clickable {
                            if ((pagerState.currentPage + 1) <= 3)
                                runBlocking {
                                    launch {
                                        pagerState.scrollToPage(pagerState.currentPage + 1)
                                    }
                                }

                        }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(chunkedList[page].size) {
                    Text(
                        text = chunkedList[page][it].toString(),
                        color =
                        when (periodType) {
                            0 -> if (chunkedList[page][it] > availableYear) Color.Black.copy(0.5f)
                            else if (chunkedList[page][it] == state.intValue) Color(61, 59, 255)
                            else Color.Black

                            1 -> if (chunkedList[page][it] < availableYear) Color.Black.copy(0.5f)
                            else if (chunkedList[page][it] == state.intValue) Color(61, 59, 255)
                            else Color.Black

                            else -> Color.Black
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = when (periodType) {
                            0 -> if (chunkedList[page][it] <= availableYear) {
                                Modifier
                                    .padding(vertical = 14.dp)
                                    .clickable {
                                        state.intValue = chunkedList[page][it]
                                    }
                            } else Modifier.padding(vertical = 14.dp)

                            1 -> if (chunkedList[page][it] >= availableYear) {
                                Modifier
                                    .padding(vertical = 14.dp)
                                    .clickable {
                                        state.intValue = chunkedList[page][it]
                                    }
                            } else Modifier.padding(vertical = 14.dp)

                            else -> Modifier
                        }

                    )
                }
            }
        }
    }
}