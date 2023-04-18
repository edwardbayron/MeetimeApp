package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.util.*

class ReservationEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = this.intent.extras?.getSerializable("reservationData") as ReservationParcelable
            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val owner = LocalViewModelStoreOwner.current

                    owner?.let{
                        val viewModel: LoanViewModel = viewModel(
                            it,
                            "LoanViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )

                        HomeContainer(viewModel)

                        val dataTest =
                                ReservationUiState(
                                    title = data.title,
                                    sum = data.sum.toString(),
                                    date = data.date.toString()
                                )

                        ReservationEditContainer(
                            dataTest,
                            onBackPress = {
                                finish()
                            }
                        ) {
                            finish()
                        }
                    }


                }
            }
        }
    }


}

@Composable
fun ReservationEditContainer(data: ReservationUiState, onBackPress: () -> Unit, onSavePress: () -> Unit) {
    Column {
        AppBarEditView(onBackPress, onSavePress)
        ReservationEditDataContainer(data)


    }
}

@Composable
fun AppBarEditView(onBackPress: () -> Unit, onSavePress: () -> Unit){
    var mDisplayMenu by remember { mutableStateOf(false) }

    androidx.compose.material.TopAppBar(
        title = {
            Text("Edit reservation",
                maxLines = 1,
                color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPress()
            }) {
                Icon(
                    Icons.Filled.ArrowBack, null,
                    tint = Color.White
                )
            }
        }, actions = {
            IconButton(
                modifier = Modifier.width(50.dp),
                onClick = {
                mDisplayMenu = !mDisplayMenu
                onSavePress()
            }) {
                Text(
                    text = "Save",
                    maxLines = 1,
                    color = Color.White
                )
            }


        })
}

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReservationEditDataContainer(data: ReservationUiState){

    var reservationTitleText = rememberSaveable { mutableStateOf(data.title) }
    var reservationSumText = rememberSaveable { mutableStateOf(data.sum) }
    var reservationDateText = rememberSaveable { mutableStateOf(data.date) }

    val gradientColors = listOf(Cyan, Color.Red, Color.Blue, Color.Yellow /*...*/)

    val brush = remember {
        Brush.linearGradient(
            colors = gradientColors
        )
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {

        Box(Modifier.fillMaxSize()){
            Column {
                TextField(
                    value = reservationTitleText.value,
                    onValueChange = { reservationTitleText.value = it },
                    textStyle = TextStyle(brush = brush))

                TextField(
                    value = reservationSumText.value,
                    onValueChange = { reservationSumText.value = it },
                    textStyle = TextStyle(brush = brush))

                TextField(
                    value = reservationDateText.value,
                    onValueChange = { reservationDateText.value = it },
                    textStyle = TextStyle(brush = brush))
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeetimeApp_v3Theme {
        ReservationEditContainer(ReservationUiState(", ", "", ""), {}) {}
    }
}