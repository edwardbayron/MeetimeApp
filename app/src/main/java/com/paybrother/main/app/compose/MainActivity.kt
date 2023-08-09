package com.paybrother.main.app.compose

import android.annotation.SuppressLint
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
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paybrother.R
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.navigation.BottomNavContentScreens.AddPostScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.HomeScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.JobScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.NetworkScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.NotificationScreen
import com.paybrother.main.app.navigation.BottomNavItem
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.main.app.viewmodels.MainViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.io.Serializable
import java.util.*

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val (appBarView, homeDataContainer, navigationGraph) = createRefs()

                    Scaffold(
                        bottomBar = { BottomNavigation(navController = navController) }
                    ) {

                        Column {
                            val owner = LocalViewModelStoreOwner.current

                            owner?.let {
                                val viewModel: LoanViewModel = viewModel(
                                    it,
                                    "LoanViewModel",
                                    MainViewModelFactory(
                                        LocalContext.current.applicationContext as Application
                                    )
                                )
                                AppBarView()
                                HomeContainer(viewModel)
                                NavigationGraph(navController = navController)
                            }


                        }

                    }


                }
            }
        }
    }
}

@Composable
fun HomeContainer(viewModel: LoanViewModel) {
    Column {
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
            IconButton(onClick = {

            }) {
                Icon(Icons.Filled.Menu, null)
            }
        }, actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Search, null)
            }
        })
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home.screen_route,
    ) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            NetworkScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            AddPostScreen()
        }
        composable(BottomNavItem.Notification.screen_route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Jobs.screen_route) {
            JobScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyNetwork,
        BottomNavItem.AddPost,
        BottomNavItem.Notification,
        BottomNavItem.Jobs
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
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
                eventName = item.event,
                name = item.name,
                number = item.phoneNumber.toInt(),
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
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.purple_700)
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
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.green_obstacle)
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
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.purple_500)
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
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.purple_700)
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
        reservation.phoneNumber,
        reservation.event,
        reservation.date
    )
    intent.putExtra("reservationData", reservationObject as Serializable)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}