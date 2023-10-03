package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.FragmentManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paybrother.R
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.fake.FakeReservationsRepository
import com.paybrother.main.app.navigation.BottomNavContentScreens.AddReservationScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.ContactsScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.EmptyScreen
import com.paybrother.main.app.navigation.BottomNavContentScreens.NotificationScreen
import com.paybrother.main.app.navigation.BottomNavItem
import com.paybrother.main.app.repository.ReservationsInteractor
import com.paybrother.main.app.repository.ReservationsRepository
import com.paybrother.main.app.repository.ReservationsRepositoryImpl
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeetimeApp_v3Theme {
                val viewModel = hiltViewModel<LoanViewModel>()
                val uiState by viewModel.uiState.collectAsState()
                val listReservations by viewModel.allReservations.observeAsState(initial = listOf())

                Log.e("MEETIME", "uiState initial name: "+uiState.name)
                Log.e("MEETIME", "uiState initial phoneNumber: "+uiState.phoneNumber)
                Log.e("MEETIME", "uiState initial event: "+uiState.event)
                Log.e("MEETIME", "uiState initial date: "+uiState.date)


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigation(navController = navController)
                        }
                    ) {

                        Column(modifier = Modifier.navigationBarsPadding()) {
                            AppBarView()
                            NavigationGraph(
                                viewModel = viewModel,
                                uiState = uiState,
                                navController = navController,
                                listReservations,
                                deleteReservation = {
                                    viewModel.deleteReservation()
                                },
                                insertReservation = {
                                    viewModel.insertReservation()
                                },
                                openReservation = {

                                    Log.e("MEETIME", "uiState openReservation name: "+uiState.name)
                                    Log.e("MEETIME", "uiState openReservation phoneNumber: "+uiState.phoneNumber)
                                    Log.e("MEETIME", "uiState openReservation event: "+uiState.event)
                                    Log.e("MEETIME", "uiState openReservation date: "+uiState.date)

                                    val intent = Bundle()
                                    intent.apply {
                                        putLong("id", 0L)
                                        putString("name", uiState?.name)
                                        putString("phoneNumber", uiState?.phoneNumber)
                                        putString("event", uiState?.event)
                                        putString("date", uiState?.date)
                                    }
                                    val reservationBottomSheet = ReservationEditBottomSheet()
                                    reservationBottomSheet.arguments = intent
                                    reservationBottomSheet.show(this@MainActivity.supportFragmentManager, "RESERVATION_EDIT_BOTTOM_SHEET")
                            }
                                )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContainer(
    viewModel: LoanViewModel,
    uiState: ReservationUiState,
    allReservations: List<Reservations>?,
    deleteReservation: () -> Unit,
    insertReservation: () -> Unit,
    openReservation: () -> Unit) {

    Column {
        HomeDataContainer(viewModel, uiState, allReservations, deleteReservation, insertReservation, openReservation)
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
fun NavigationGraph(
    viewModel: LoanViewModel,
    uiState: ReservationUiState,
    navController: NavHostController,
    allReservations: List<Reservations>?,
    deleteReservation: () -> Unit,
    insertReservation: () -> Unit,
    openReservation: () -> Unit) {

    NavHost(
        navController,
        startDestination = BottomNavItem.Home.screen_route,
    ) {
        composable(BottomNavItem.Home.screen_route) {
            HomeContainer(
                viewModel = viewModel,
                uiState = uiState,
                allReservations,
                deleteReservation,
                insertReservation,
                openReservation,
            )
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            EmptyScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            AddReservationScreen()
        }
        composable(BottomNavItem.Notification.screen_route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Jobs.screen_route) {
            ContactsScreen()
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
fun HomeDataContainer(
    viewModel : LoanViewModel,
    uiState: ReservationUiState,
    allReservations: List<Reservations>?,
    deleteReservation: () -> Unit,
    insertReservation: () -> Unit,
    openReservation: () -> Unit) {

    Box (modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            allReservations?.forEach { item ->
                ReservationElementView(
                    eventName = item.event,
                    name = item.name,
                    number = item.phoneNumber,
                    date = item.date,
                    onCardClick = {
                        //openReservationActivity(this, item, ReservationUiState(item.id, item.name, item.phoneNumber, item.event, item.date))
                        viewModel.selectedReservation(uiState.copy(item.id, item.name, item.phoneNumber, item.event, item.date))
                        openReservation()
                    },
                    onDeleteClick = {
                        deleteReservation()
                    })
            }
        }
        FloatinActionButton(uiState, insertReservation)
    }
}

@Composable
fun FloatinActionButton(uiState: ReservationUiState, insertReservation: () -> Unit){

    val addReservation = remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(bottom = 70.dp, end = 10.dp),
            onClick = {
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
        AddReservationDialog(uiState = uiState, insertReservation)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReservationDialog(uiState: ReservationUiState, insertReservation: () -> Unit) {

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
                            reservationName.value = it
                            uiState.name = it
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
                            reservationPhoneNumber.value = it
                            uiState.phoneNumber = it
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
                            reservationEvent.value = it
                            uiState.event = it
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
                            reservationDate.value = it
                            uiState.date = it
                        })

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            insertReservation()

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
    MeetimeApp_v3Theme {
        HomeContainer(
            LoanViewModel(ReservationsInteractor(FakeReservationsRepository())),
            ReservationUiState(1, ", ", "", "", ""),
            listOf(),
            {},
            {},
            {}
        )
    }
}

//private fun openReservationActivity(reservation: Reservations, uiState: ReservationUiState) {
//    val intent = Intent(activity, ReservationEditActivity::class.java)
//
//    intent.putExtra("id", uiState.id)
//    intent.putExtra("name", uiState.name)
//    intent.putExtra("phoneNumber", uiState.phoneNumber)
//    intent.putExtra("event", uiState.event)
//    intent.putExtra("date", uiState.date)
//
//
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    activity.startActivity(intent)
//
//    ReservationEditBottomSheet().show(activity, "RESERVATION_EDIT_BOTTOM_SHEET")
//
//}