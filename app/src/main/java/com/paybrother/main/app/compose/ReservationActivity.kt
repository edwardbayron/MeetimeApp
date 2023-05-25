package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.main.app.data.ReservationData
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.utils.Utils
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
import java.util.*

class ReservationActivity : ComponentActivity() {

    private lateinit var dataReceived: ReservationUiState
    private lateinit var viewModel : LoanViewModel

    private val openEditReservationActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val resultCode = it.resultCode
        val data = it.data

        when (resultCode) {
            Activity.RESULT_OK -> {

                dataReceived = ReservationUiState(
                    data?.getLongExtra("id", 0L),
                    data?.getStringExtra("title").toString(),
                    data?.getStringExtra("sum").toString(),
                    data?.getStringExtra("date").toString()
                )

                viewModel.selectedReservation(dataReceived)

            }

            Activity.RESULT_CANCELED -> {
                viewModel.selectedReservation(dataReceived)
            }
            else -> {
                Toast.makeText(this, "Edit Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val data =
                this.intent.extras?.getSerializable("reservationData") as ReservationParcelable

            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                viewModel = viewModel(
                    it,
                    "LoanViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext as Application
                    )
                )
            }

            viewModel.selectedReservation(
                ReservationUiState(
                    id = data.id,
                    title = data.title,
                    sum = data.sum.toString(),
                    date = data.date.toString()
                ))

            val uiState = viewModel.uiState.observeAsState()

            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservationContainer(data = uiState.value!!, { finish() })
                }
            }
        }
    }


    @Composable
    fun ReservationContainer(data: ReservationUiState, onBackPress: () -> Unit) {
        Column {
            AppBarView(data, onBackPress)
            ReservationDataScreen(data, onBackPress)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBarView(data: ReservationUiState, onBackPress: () -> Unit) {
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
                            data.id!!,
                            data.title,
                            data.sum.toInt(),
                            Utils.convertStringToDate2("2010-05-30")
                        )

                        var reservationObject = ReservationParcelable(
                            reservationData.id,
                            reservationData.title,
                            reservationData.sum,
                            reservationData.date
                        )
                        intent.putExtra("reservationData", reservationObject as Serializable)

                        openEditReservationActivityLauncher.launch(intent)

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
            ReservationContainer(ReservationUiState(1234567890L, "title", "sum", "date"), {})
        }
    }
}
