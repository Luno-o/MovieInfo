package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.entity.MovieCollectionImp
import com.example.movieinfo.entity.MovieForStaff
import com.example.movieinfo.entity.ProfessionKey
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.entity.StaffFullInfo
import com.example.movieinfo.entity.actorCardForPreview
import com.example.movieinfo.entity.emptyMovieBaseInfo
import com.example.movieinfo.presentation.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FilmographyPageView(
    viewModel: MainViewModel,
    navController: NavController
) {
   val listMovieBaseInfo:Map<MovieCollectionImp,String?>
   = viewModel.staffForMovieCollections.collectAsState().value
    val staffInfo: StaffFullInfo = viewModel.staff.collectAsState().value
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    var listOfMovie by remember { mutableStateOf<List<MovieCollectionImp>>(emptyList()) }

    val tabList = listOf("Писатель", "Оператор",
        "Редактор", "Композитор", "Продюсер СССР",
        "Переводчик", "Директор", "Дизайнер",
        "Продюсер озвучки", "Актер", "Режиссер",
        "Неизвестно",
        "Себя")

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
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
                text = stringResource(R.string.filmography),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold

            )
            Box(
                modifier = Modifier
                    .weight(1f)
            )
        }
        Text(text = staffInfo.nameRU!!, fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            maxLines = 1)
        GalleryTabSection (tabList = tabList, tabAddInfo = null){
            selectedTabIndex = it
        }

        when (selectedTabIndex) {
            ProfessionKey.ACTOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.ACTOR.name }.map{ it.key }
            ProfessionKey.DESIGN.ordinal ->listOfMovie = listMovieBaseInfo
            .filterValues { it == ProfessionKey.DESIGN.name }.map{ it.key }
            ProfessionKey.DIRECTOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.DIRECTOR.name }.map{ it.key }
            ProfessionKey.VOICE_DIRECTOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.VOICE_DIRECTOR.name }.map{ it.key }
            ProfessionKey.EDITOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.EDITOR.name }.map{ it.key }
            ProfessionKey.PRODUCER.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.PRODUCER.name }.map{ it.key }
            ProfessionKey.PRODUCER_USSR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.PRODUCER_USSR.name }.map{ it.key }
            ProfessionKey.OPERATOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.OPERATOR.name }.map{ it.key }
            ProfessionKey.TRANSLATOR.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.TRANSLATOR.name }.map{ it.key }
            ProfessionKey.COMPOSER.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.COMPOSER.name }.map{ it.key }
            ProfessionKey.HERSELF.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.HERSELF.name }.map{ it.key }
            ProfessionKey.WRITER.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.WRITER.name }.map{ it.key }
            ProfessionKey.UNKNOWN.ordinal ->listOfMovie = listMovieBaseInfo
                .filterValues { it == ProfessionKey.UNKNOWN.name }.map{ it.key }


        }
                LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 8.dp)) {
listOfMovie.forEachIndexed { index, movieCollection ->
    item {
    FilmographyMovieItem(movieCard1 = movieCollection)
    }
}
            }
    }
}
