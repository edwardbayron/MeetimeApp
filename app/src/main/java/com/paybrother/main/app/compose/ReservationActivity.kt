package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paybrother.main.app.data.ReservationData
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.*

@AndroidEntryPoint
class ReservationActivity : ComponentActivity() {

//    private val openEditReservationActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        val resultCode = it.resultCode
//        val data = it.data
//
//        when (resultCode) {
//            Activity.RESULT_OK -> {
//
//                dataReceived = ReservationUiState(
//                    data?.getLongExtra("id", 0L),
//                    data?.getStringExtra("name").toString(),
//                    data?.getStringExtra("phoneNumber").toString(),
//                    data?.getStringExtra("event").toString(),
//                    data?.getStringExtra("date").toString()
//                )
//
//                viewModel.selectedReservation(dataReceived)
//
//            }
//
//            Activity.RESULT_CANCELED -> {
//            }
//            else -> {
//                Toast.makeText(this, "Edit Cancelled", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<LoanViewModel>()
            val uiState by viewModel.uiState.collectAsState()
//
//            viewModel.selectedReservation(
//                ReservationUiState(
//                    id = uiState.id,
//                    name = uiState.name,
//                    phoneNumber = uiState.phoneNumber,
//                    event = uiState.event,
//                    date = uiState.date.toString()
//                ))


            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservationContainer(uiState = uiState, { finish() }, {
                        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                            val resultCode = it.resultCode
                            val data = it.data

                            when (resultCode) {
                                Activity.RESULT_OK -> {
//                                    val state = viewModel.savedUiState.apply {
//                                        this?.id = data?.getLongExtra("id", 0L)
//                                        this?.name = data?.getStringExtra("name").toString()
//                                        this?.phoneNumber = data?.getStringExtra("phoneNumber").toString()
//                                        this?.event = data?.getStringExtra("event").toString()
//                                        this?.date = data?.getStringExtra("date").toString()
//
//                                    }
//                                    dataReceived = ReservationUiState(
//                                        data?.getLongExtra("id", 0L),
//                                        data?.getStringExtra("name").toString(),
//                                        data?.getStringExtra("phoneNumber").toString(),
//                                        data?.getStringExtra("event").toString(),
//                                        data?.getStringExtra("date").toString()
//                                    )

                                    //viewModel.selectedReservation(uiState)

                                }

                                Activity.RESULT_CANCELED -> {
                                }

                                else -> {
                                    Toast.makeText(this, "Edit Cancelled", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }.launch(intent)


                    })
                }
            }
        }
    }


    @Composable
    fun ReservationContainer(uiState: ReservationUiState, onBackPress: () -> Unit, onReservationEditOpen: () -> Unit) {
        Column {
            AppBarView(uiState, onBackPress, onReservationEditOpen)
            ReservationDataScreen(uiState, onBackPress)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBarView(uiState: ReservationUiState, onBackPress: () -> Unit, onReservationEditOpen: () -> Unit) {
        val mContext = LocalContext.current
        var mDisplayMenu by remember { mutableStateOf(false) }
        var editEnabled by remember { mutableStateOf(false) }


        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            title = {
                Text("Reservation Details")
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBackPress()
                    editEnabled = false
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }, actions = {
                IconButton(onClick = {
                    mDisplayMenu = !mDisplayMenu
                }) {
                    Icon(Icons.Filled.MoreVert, null)
                }

                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {

                    DropdownMenuItem(text = {
                        Text(text = "Edit")
                    }, onClick = {
                        editEnabled = true

                        val intent =
                            Intent(this@ReservationActivity, ReservationEditActivity::class.java)

                        val reservationData = ReservationData(
                            uiState.id!!,
                            uiState.name,
                            uiState.phoneNumber,
                            uiState.event,
                            "2010-05-30"
                        )

                        var reservationObject = ReservationParcelable(
                            reservationData.id,
                            reservationData.name,
                            reservationData.phoneNumber,
                            reservationData.event,
                            reservationData.date
                        )
                        intent.putExtra("reservationData", reservationObject as Serializable)

                        //openEditReservationActivityLauncher.launch(intent)
                        onReservationEditOpen()






                        mDisplayMenu = false
                    })

                    DropdownMenuItem(text = {
                        Text(text = "Delete")
                    }, onClick = {
                        Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show()
                    })
                }
            })
    }

    @SuppressLint("UnrememberedMutableState")
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        MeetimeApp_v3Theme {
            ReservationContainer(ReservationUiState(
                id = 1234567890L,
                name = "title",
                phoneNumber = "58053317",
                event = "event",
                date = "1992-07-15",
                time = "18:00",
                notificationText = "test",
                notificationTime = "18:00"
                ), {}, {})
        }
    }
}
