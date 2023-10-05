package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReservationEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetimeApp_v3Theme {
                val viewModel = hiltViewModel<LoanViewModel>()

                val testUiState = ReservationUiState(
                    id = intent?.getLongExtra("id", 0L),
                    name = intent?.getStringExtra("name").toString(),
                    phoneNumber = intent?.getStringExtra("phoneNumber").toString(),
                    event = intent?.getStringExtra("event").toString(),
                    date = intent?.getStringExtra("date").toString()
                )


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ReservationEditContainer(uiState = testUiState, activity = this, viewModel = viewModel)
                }
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun ReservationEditContainer(uiState: ReservationUiState, activity: ComponentActivity, viewModel: LoanViewModel){
    ReservationEditContainer(
        uiState,
        onBackPress = {
            activity.setResult(RESULT_CANCELED)
            activity.finish()
        },
        onSavePress = {
            viewModel.updateReservation(state = uiState)
            // TODO
            val intent = Intent()
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    )
}

@Composable
fun ReservationEditContainer(
    uiState: ReservationUiState,
    onBackPress: () -> Unit,
    onSavePress: () -> Unit
) {
    Column {
        AppBarEditView(onBackPress)
        ReservationEditDetails(uiState, onSavePress)
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
fun ReservationEditDetails(
    uiState: ReservationUiState,
    onSavePress: () -> Unit
) {
    val reservationTitleText = remember { mutableStateOf(uiState.name) }
    val reservationPhoneNumberText = remember { mutableStateOf(uiState.phoneNumber) }
    val reservationEventText = remember { mutableStateOf(uiState.event)}
    val reservationDateText = remember { mutableStateOf(uiState.date) }

    val reservationDetailsUiState = remember { mutableStateOf(uiState) }

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
                    onValueChange = {
                        reservationTitleText.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = reservationPhoneNumberText.value,
                    onValueChange = {
                        reservationPhoneNumberText.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = reservationEventText.value,
                    onValueChange = {
                        reservationEventText.value = it
                    },
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
                    onValueChange = {
                        reservationDateText.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        //onSavePress(uiState)
                        onSavePress()
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
        ReservationEditContainer(ReservationUiState(1234567890L,"Title", "Sum", "event","2010-05-30"), {}, {})
    }
}