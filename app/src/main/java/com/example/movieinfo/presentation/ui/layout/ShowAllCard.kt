package com.example.movieinfo.presentation.ui.layout


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.navigate
import timber.log.Timber


@Composable
fun ShowAllCardView(navController: NavController, movieType: String?, kpID: String? = "0") {

    Box(modifier = Modifier
        .size(111.dp, 156.dp)
        .clickable {
            navController.navigate("${MovieInfoDestination.COLLECTION_ROUTE}/$movieType/$kpID")
        }) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(111.dp, 50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_circle_right_24),
                contentDescription = null,
                Modifier
                    .background(Color(61, 59, 255), RoundedCornerShape(100))
                    .border(BorderStroke(4.dp, Color.White), RoundedCornerShape(100))
                    .align(Alignment.TopCenter)
            )
            Text(
                text = "Показать все",
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
