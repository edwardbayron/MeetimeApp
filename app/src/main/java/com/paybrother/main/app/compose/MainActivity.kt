package com.paybrother.main.app.compose

import android.R
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.utils.Utils
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
import java.time.format.TextStyle
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
fun AppBarView() {
    TopAppBar(
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
fun HomeDataContainer(viewModel: LoanViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        val listReservations by viewModel.allReservations?.observeAsState(listOf())!!
        val addReservation = remember { mutableStateOf(false) }

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


        Box(Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = {
                    //viewModel.insertReservation()

                    addReservation.value = true


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

        if (addReservation.value) {
            AddReservationDialog(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReservationDialog(viewModel: LoanViewModel) {
    val txtFieldError = remember { mutableStateOf("") }
    val reservationName = remember { mutableStateOf("") }
    val reservationPhoneNumber = remember { mutableStateOf("") }
    val reservationEvent = remember { mutableStateOf("") }
    val reservationDate = remember { mutableStateOf("") }
    val shouldDismiss = remember { mutableStateOf(false) }

    if (shouldDismiss.value) return

    Dialog(
        onDismissRequest = {
            shouldDismiss.value = true
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
        ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Add reservation",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    TextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(10)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter name") },
                        value = reservationName.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            reservationName.value = it.take(10)
                        })

                    TextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(10)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter phoneNumber") },
                        value = reservationPhoneNumber.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            reservationPhoneNumber.value = it.take(10)
                        })

                    TextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(10)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter event") },
                        value = reservationEvent.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            reservationEvent.value = it.take(10)
                        })

                    TextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(10)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter date") },
                        value = reservationDate.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            reservationDate.value = it.take(10)
                        })

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.insertReservation(
                                reservationName.value,
                                reservationPhoneNumber.value,
                                reservationEvent.value,
                                reservationDate.value
                            )

                            shouldDismiss.value = true
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Save")
                    }
                }
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

private fun openReservationActivity(context: Context, reservation: Reservations) {
    val intent = Intent(context, ReservationActivity::class.java)
    val reservationObject = ReservationParcelable(
        reservation.id!!,
        reservation.name,
        reservation.id?.toInt()!!,
        Utils.convertStringToDate(reservation.date)!!
    )
    intent.putExtra("reservationData", reservationObject as Serializable)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}