package com.example.movieinfo.presentation.ui.layout.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieinfo.R
import kotlinx.coroutines.launch

data class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(
                    R.drawable.onboarding_img_1,
                    R.string.onBoardingTitle1,
                    R.string.onBoardingText1
                ),
                OnBoardingItems(
                    R.drawable.onboarding_img_2,
                    R.string.onBoardingTitle2,
                    R.string.onBoardingText2
                ),
                OnBoardingItems(
                    R.drawable.onboarding_img_3,
                    R.string.onBoardingTitle3,
                    R.string.onBoardingText3
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingView() {
    val items = OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        items.size
    }
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        TopSection(onBackClick =
        {
            if (pagerState.currentPage + 1 > 1) scope.launch {
                pagerState.scrollToPage(pagerState.currentPage - 1)
            }
        },
            onSkipClick = {
                if (pagerState.currentPage + 1 < items.size) scope.launch {
                    pagerState.scrollToPage(items.size - 1)
                }
            })
    HorizontalPager(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(),
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        beyondViewportPageCount = 0,
        pageSize = PageSize.Fill,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(pagerState,
            Orientation.Horizontal
        ),
        pageContent =  {
            OnBoardingItem(items = items[it])
        }
    )
    BottomSection(size = items.size, index = pagerState.currentPage) {
        if (pagerState.currentPage + 1 < items.size) scope.launch {
            pagerState.scrollToPage(pagerState.currentPage + 1)
        }
    }
    }

}

@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {// Back Button
//        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
//            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
//        }
        Text(text = "MovieInfo", fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterStart))
        //Skip Button
        TextButton(
            onClick = onSkipClick, modifier = Modifier
                .align(Alignment.CenterEnd)
                .alpha(0.3f),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Пропустить", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, onButtonClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        //Indicators
        Indicators(size, index)
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 10.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) Color.Black else Color(
                    0xfff8e2e7
                )
            )
    )
}

@Composable
fun OnBoardingItem(items: OnBoardingItems) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = stringResource(id = items.desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 48.dp)
                .align(Alignment.Center)
                .fillMaxWidth())
        Text(text = stringResource(id = items.title),
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 48.dp, start = 24.dp))
    }
}
@Preview
@Composable
private fun OnBoardingPreview() = OnboardingView()