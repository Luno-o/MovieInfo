package com.example.movieinfo.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movieinfo.R
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@Composable
fun ProfilePageView(viewModel: MainViewModel,
                    navController: NavController) {
    val scrollState = rememberScrollState()
    val listOfCollection = viewModel.collectionsList.collectAsState().value
    val collections = viewModel.yourCollections.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    val stateDialog = rememberTextFieldState("")


    runBlocking { viewModel.viewModelScope.launch() {
        Timber.d("my collections size ${listOfCollection.size}")
        Timber.d("your collections size ${collections.size}")
        viewModel.loadWatchedMovie()
        viewModel.loadYourInterestMovie()
        if (collections.isEmpty()){
        viewModel.getCollectionsList()
        Timber.d("my collections $listOfCollection")
        viewModel.loadYourCollections(listOfCollection)
        }
    } }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        if (showDialog) CreateCollectionDialog(onDismissRequest =
        {showDialog = false}, onConfirmation = {viewModel.addCollection(stateDialog.text.toString())
                                               showDialog = false},
            state = stateDialog)
        MovieCollectionView(
            viewModel,
            viewModel.watchedMovie,
            stringResource(R.string.watched),
            CollectionType.WATCHED,
            navController,
            allOrCount = false,
            showOrDelete = false,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.collections),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {

                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable {
showDialog = true
                }
            )
            Text(
                text = stringResource(R.string.make_own_collection),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(332.dp)
                .padding(vertical = 16.dp)
        ) {
            items(collections.size) {
                if (viewModel.yourCollections.value.isNotEmpty())
                CollectionGridItem(collectionName = listOfCollection[it].collectionName,
                    collectionSize = collections[it].size,
                    navController = navController)
            }
        }
        MovieCollectionView(
            viewModel,
            viewModel.yourInterest,
            stringResource(R.string.by_ur_interest),
            CollectionType.INTEREST,
            navController, allOrCount = false,
            showOrDelete = false
        )
    }
}

    @Composable
    fun CreateCollectionDialog(onDismissRequest: () -> Unit,
                               onConfirmation: () -> Unit,
                               placeholderText: String =
                                   "Придумайте название для вашей новой коллекции",
                               state: TextFieldState){

        Dialog(onDismissRequest = {onDismissRequest()}) {
            Card(
                modifier = Modifier
                    .width(308.dp)
                    .height(240.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Icon(painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.End).padding(8.dp).clickable {
onDismissRequest()
                        })

BasicTextField(state = state, modifier = Modifier.height(100.dp)
    .padding(horizontal =   12.dp),
    decorator = {innerTextField ->
        Row(
            verticalAlignment = Alignment.Top
        ) {

            Box(Modifier.weight(1f)) {
                if (state.text.isEmpty()) {
                    Text(
                        text = placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        ),
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(Alignment.CenterStart)
                        , fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }

    }, textStyle = TextStyle.Default.copy(fontSize = 16.sp)
)

                        Button(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp)
                                .align(Alignment.End),
                            shape = ButtonDefaults.textShape,
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = Color(61,59,255)),
                        ) {
                            Text("Готово", color = Color.White, modifier = Modifier.padding(0.dp))
                        }

                }
            }
        }
    }
@Composable
fun ErrorDialog(){
   Dialog(onDismissRequest = {}) {
       Card(
           modifier = Modifier
               .width(360.dp)
               .height(200.dp),
           shape = RoundedCornerShape(16.dp),
       ) {
           Box(modifier = Modifier.fillMaxSize()){
           Icon(painter = painterResource(R.drawable.baseline_close_24),
               contentDescription = null,
               modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).clickable {

               })
           Text(text = "Ошибка!", fontSize = 18.sp, fontWeight = FontWeight.Bold,
               modifier = Modifier.align(Alignment.TopStart).padding(24.dp)
           )
           Text(
               text = "Во время обработки запроса произошла ошибка",
               modifier = Modifier
                   .padding(horizontal = 24.dp, vertical = 24.dp)
                   .align(Alignment.Center),
               color = Color.Black.copy(alpha = 0.5f),
               fontSize = 16.sp
           )
           }
       }
   }
}

@Composable
@Preview
fun ErrorDialogPrev() = ErrorDialog()
