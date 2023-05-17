package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.paybrother.main.app.data.ReservationData
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.utils.Utils
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReservationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = this.intent.extras?.getSerializable("reservationData") as ReservationParcelable


            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val dataTest =
                        ReservationUiState(
                            title = data.title,
                            sum = data.sum.toString(),
                            date = data.date.toString()
                        )

                    ReservationContainer(
                        dataTest
                    ) {
                        finish()
                    }
                }
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
                    openReservationEditActivity(mContext, ReservationData("", data.title, data.sum.toInt(), Utils.convertStringToDate2("2010-05-30")))
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
        ReservationContainer(ReservationUiState("title", "sum", "date"), {})
    }
}

private fun openReservationEditActivity(context: Context, reservation: ReservationData) {
    val intent = Intent(context, ReservationEditActivity::class.java)
    val reservationObject =
        ReservationParcelable(reservation.id, reservation.title, reservation.sum, reservation.date)
    intent.putExtra("reservationData", reservationObject as Serializable)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    context.startActivity(intent)
}
