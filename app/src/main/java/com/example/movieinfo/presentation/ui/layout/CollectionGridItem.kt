package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.utils.MovieInfoDestination
import com.example.movieinfo.R

@Composable
fun CollectionGridItem(
    modifier: Modifier = Modifier, collectionName: String,
    collectionSize: Int, navController: NavController
) {
    val icon: Painter = when (collectionName) {
        stringResource(R.string.favourite) -> painterResource(id = R.drawable.favourite)
        stringResource(R.string.bookmarks) -> painterResource(id = R.drawable.mark)
        else -> painterResource(id = R.drawable.profile_icon)
    }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(146.dp, 146.dp)
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("${MovieInfoDestination.COLLECTION_ROUTE}/$collectionName")
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(39, 39, 39))
            )
            Text(
                text = collectionName,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = collectionSize.toString(),
                fontSize = 8.sp,
                color = Color.White, modifier = Modifier
                    .background(
                        Color(61, 59, 255),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            )
        }
    }


}
