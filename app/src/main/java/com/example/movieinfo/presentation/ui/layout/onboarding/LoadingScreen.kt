package com.example.movieinfo.presentation.ui.layout.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieinfo.R

@Composable
fun LoadingScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {// Back Button
        Text(
            text = "MovieInfo", fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(24.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.onboarding_img_1),
            contentDescription = stringResource(id = R.string.onBoardingText1),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }

}

@Preview
@Composable
fun LoadingScreenPreview() = LoadingScreen()