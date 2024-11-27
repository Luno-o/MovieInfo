package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.MovieInfoDestination
import com.example.movieinfo.R
import kotlinx.coroutines.runBlocking

data class ActorCard(val role: String?, val actorName: String?, val phUrl: String?) {
}

@Composable
fun ActorCardView(actor: ActorCard, navController: NavController, staffID: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .wrapContentSize()
        .background(
            Color.White
        )
        .clickable {
            navController.navigate("${MovieInfoDestination.DETAIL_STAFF}/$staffID")
        }) {
        GlideImageWithPreview(data = actor.phUrl, Modifier.size(49.dp, 68.dp))
        Column {
            Text(
                text = actor.actorName ?: stringResource(R.string.no_info),
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = actor.role ?: stringResource(R.string.no_info),
                color = Color.Black.copy(0.5f),
                fontSize = 12.sp
            )
        }
    }
}