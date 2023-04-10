package com.paybrother.main.app.compose

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.main.app.data.ReservationData
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
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
                    val owner = LocalViewModelStoreOwner.current

                    owner?.let{
                        val viewModel: LoanViewModel = viewModel(
                            it,
                            "LoanViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )

                        //viewModel.fetchReservationData()
                        HomeContainer(viewModel)
                    }



                    ReservationContainer(
                        data,
                        onBackPress = {
                            onBackPressed()
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun ReservationContainer(data: ReservationParcelable, onBackPress: () -> Unit) {
    Column {
        AppBarView(onBackPress, data)
        ReservationDataContainer(data)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(onBackPress: () -> Unit, data: ReservationParcelable){
    val mContext = LocalContext.current
    var mDisplayMenu by remember { mutableStateOf(false) }

    androidx.compose.material.TopAppBar(
        //elevation = 4.dp,
        title = {
            Text("Reservation Details")
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPress()
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
                    openReservationEditActivity(mContext, ReservationData(data.id, data.title, data.sum, data.date))
                })

                DropdownMenuItem(text = {
                    Text(text = "Delete")
                }, onClick = {
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show()
                })


            }


        })
}


@Composable
fun ReservationDataContainer(data: ReservationParcelable){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {

        Box(Modifier.fillMaxSize()){
            Column {
                Text(text = data.title)
                Text(text = data.sum.toString())
                Text(text = data.date.toString())
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MeetimeApp_v3Theme {
        ReservationContainer(ReservationParcelable(", ", "", 0, Date()), {})
    }
}

private fun openReservationEditActivity(context: Context, reservation: ReservationData){
    val intent = Intent(context, ReservationEditActivity::class.java)
    val reservationObject = ReservationParcelable(reservation.id, reservation.title, reservation.sum, reservation.date)
    intent.putExtra("reservationData", reservationObject as Serializable)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    context.startActivity(intent)
}
