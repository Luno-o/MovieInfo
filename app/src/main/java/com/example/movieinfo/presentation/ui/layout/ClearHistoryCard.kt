package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfo.R
import com.example.movieinfo.presentation.ui.viewModels.ProfileViewModel

@Composable
fun ClearHistoryCardView(profileViewModel: ProfileViewModel = viewModel(), collectionName: String) {
    Box(modifier = Modifier.size(111.dp, 156.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(111.dp, 80.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.basket1),
                contentDescription = null,
                Modifier
                    .align(Alignment.Center)
                    .padding(start = 20.dp)
                    .clickable {
                        profileViewModel.clearHistory(collectionName)
                    }
            )
            Text(
                text = stringResource(R.string.clear_history),
                fontSize = 12.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 24.dp, start = 8.dp)
            )
        }
    }
}