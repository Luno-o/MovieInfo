package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieinfo.R
import com.example.movieinfo.entity.collectionRow

//class TestCompose {
//    @Composable
//    fun testColumn() {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Green),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Text(text = "Hello")
//            Text(text = "World")
//        }
//    }

//    @Preview
//    @Composable
//    fun testColumnPreview() = testColumn()
//}

class Language(val name: String, val hexColor: Long)

@Composable
fun TestViewLazyGrid() {
    val langs = listOf(
        Language("Kotlin", 0xff16a085),
        Language("Java", 0xff2980b9),
        Language("JavaScript", 0xff8e44ad),
        Language("Python", 0xff2c3e50),
        Language("Rust", 0xffd35400),
        Language("C#", 0xff27ae60),
        Language("C++", 0xfff39c12),
        Language("Go", 0xff1abc9c)
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start
    ) {
        items(langs) { lang ->
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color(lang.hexColor)))
                Text(lang.name, fontSize = 24.sp, modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun TestIcon(){
    IconButton(onClick =
    {
    }
        , modifier = Modifier.size(34.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.favourite),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = Color.LightGray
        )
    }
}
@Composable
@Preview
fun testIconPrev() = TestIcon()
@Composable
@Preview
fun TestGridPrev() = TestViewLazyGrid()
@Composable
fun MovieGridView(movieCollectionRow: MovieCollectionRow,modifier: Modifier= Modifier){

}
@Preview
@Composable
fun MovieGridPreview() = MovieGridView(movieCollectionRow = collectionRow)