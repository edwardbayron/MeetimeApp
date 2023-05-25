package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
import java.util.*

class ReservationEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data =
                this.intent.extras?.getSerializable("reservationData") as ReservationParcelable
            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        val viewModel: LoanViewModel = viewModel(
                            it,
                            "LoanViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )

                        val dataTest =
                            ReservationUiState(
                                id = data.id,
                                title = data.title,
                                sum = data.sum.toString(),
                                date = data.date.toString()
                            )

                        openEditContainer(dataTest, this, viewModel)
                    }
                }
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun openEditContainer(dataTest: ReservationUiState, activity: ComponentActivity, viewModel: LoanViewModel){
    ReservationEditContainer(
        dataTest,
        onBackPress = {
            activity.setResult(RESULT_CANCELED)
            activity.finish()
        },
        onSavePress = { state ->
            Log.e("TEST", "onSavePress state title: "+state?.title)
            viewModel.updateReservation(state = state)
            val intent = Intent()
            intent.putExtra("id", state.id)
            intent.putExtra("title", state.title)
            intent.putExtra("sum", state.sum)
            intent.putExtra("date", state.date)
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    )
}

@Composable
fun ReservationEditContainer(
    data: ReservationUiState,
    onBackPress: () -> Unit,
    onSavePress: (state: ReservationUiState) -> Unit
) {
    Column {
        AppBarEditView(onBackPress)
        ReservationEditDataContainer(data, onSavePress)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarEditView(
    onBackPress: () -> Unit,
) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                "Edit reservation",
                maxLines = 1,
                color = Color.White
            )
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
                }) {
                Text(
                    text = "Save", // TODO doesn't work, think about another approach or fix it!!!!
                    maxLines = 1,
                    color = Color.White
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationEditDataContainer(
    data: ReservationUiState,
    onSavePress: (state: ReservationUiState) -> Unit
) {
    val reservationTitleText = rememberSaveable { mutableStateOf(data.title) }
    val reservationSumText = rememberSaveable { mutableStateOf(data.sum) }
    val reservationDateText = rememberSaveable { mutableStateOf(data.date) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        Box(Modifier.fillMaxSize()) {
            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = reservationTitleText.value,
                    onValueChange = { reservationTitleText.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = reservationSumText.value,
                    onValueChange = { reservationSumText.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = reservationDateText.value,
                    onValueChange = { reservationDateText.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                val savedData = ReservationUiState(
                    data.id,
                    reservationTitleText.value,
                    reservationSumText.value,
                    reservationDateText.value
                )

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onSavePress(savedData)
                    }) {

                    Text("Save")
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeetimeApp_v3Theme {
        ReservationEditContainer(ReservationUiState(1234567890L,"Title", "Sum", "2010-05-30"), {}, {})
    }
}