package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieinfo.R
import com.example.movieinfo.presentation.MainViewModel

@Composable
fun ClearHistoryCardView(viewModel: MainViewModel,collectionName: String){
    Box(modifier = Modifier.size(111.dp, 156.dp)){
        Box(modifier = Modifier
            .align(Alignment.Center)
            .size(111.dp,80.dp)){
            Image(painter = painterResource(id = R.drawable.basket1),
                contentDescription = null,
                Modifier.align(Alignment.Center).padding(start = 20.dp).clickable {
viewModel.clearHistory(collectionName)
                }
            )
            Text(text = "Очистить историю",
                fontSize = 12.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(top = 24.dp, start = 8.dp))
        }
    }
}