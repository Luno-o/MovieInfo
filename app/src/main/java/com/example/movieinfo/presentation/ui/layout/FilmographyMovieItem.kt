package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieinfo.utils.convert
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.entity.MovieBaseInfo

@Composable
fun FilmographyMovieItem(
    movieCard1: MovieCollectionImpl? = null,
    movieCard2: MovieBaseInfo? = null
) {
    val movieCard = movieCard2?.convert() ?: movieCard1
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            GlideImageWithPreview(
                data = movieCard?.prevPosterUrl,
                Modifier
                    .size(111.dp, 156.dp)
                    .background(Color.LightGray)
            )

            Text(
                text = movieCard?.raitingKP.toString(),
                fontSize = 6.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 4.dp, start = 4.dp)
                    .background(Color.White, RoundedCornerShape(40))
                    .padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp)

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text(
                text = movieCard?.nameRU.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                modifier = Modifier.padding(start = 16.dp)

            )
            Text(
                text = "${movieCard?.year ?: ""}, ${movieCard?.genre?.joinToString { it.genre }}",
                fontSize = 12.sp,
                maxLines = 1,
                color = Color.Black.copy(0.5f),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}