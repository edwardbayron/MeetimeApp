package com.paybrother.main.app.compose

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.LoanData
import com.paybrother.main.app.data.LoanParcelable
import com.paybrother.main.app.utils.Utils
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import java.io.Serializable
import java.text.DateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetimeApp_v3Theme {

                // A surface container using the 'background' color from the theme
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
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContainer(viewModel: LoanViewModel) {
    Column {
        AppBarView()
        HomeDataContainer(viewModel)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(){
    TopAppBar(
        //elevation = 4.dp,
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text("Home")
        },
        navigationIcon = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Menu, null)
            }
        }, actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Search, null)
            }
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Settings, null)
            }
        })
}

@Composable
fun HomeDataContainer(viewModel: LoanViewModel){
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {

        val listReservations by viewModel.allReservations?.observeAsState(listOf())!!

        listReservations.forEach { item ->
            ReservationElementView(
                title = item.name,
                sum = item.id?.toInt()!!,
                date = item.date,
                onCardClick = {
                    openReservationActivity(context, item)
                },
                onDeleteClick = {
                    viewModel.deleteReservation(item.name)
                })
        }


        Box(Modifier.fillMaxSize()){
            FloatingActionButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = {
                    viewModel.insertReservation()
                },
                containerColor = Color.Red,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val context = LocalContext.current
    MeetimeApp_v3Theme {
        HomeContainer(LoanViewModel(context.applicationContext as Application))
    }
}

private fun openReservationActivity(context: Context, reservation: Reservations){
    val intent = Intent(context, ReservationActivity::class.java)
    val reservationObject = LoanParcelable(reservation.id.toString(), reservation.name, reservation.id?.toInt()!!, Utils.convertStringToDate(reservation.date)!!)
    intent.putExtra("reservationData", reservationObject as Serializable)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    context.startActivity(intent)
}