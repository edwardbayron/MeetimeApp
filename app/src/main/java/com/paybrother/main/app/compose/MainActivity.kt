package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.paybrother.main.app.viewmodels.LoanViewModel
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import dagger.hilt.android.AndroidEntryPoint
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

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomNavigation(navController = navController)
                        },
                        topBar = {
                            AppBarView()
                        },
                        content = {
                            Box(modifier = Modifier.padding(it)) {
                                NavigationGraph(
                                    activity = this@MainActivity,
                                    viewModel = viewModel,
                                    uiState = uiState,
                                    navController = navController,
                                    listReservations
                                )
                            }
                        }
                    )
                }
            }
        }
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
fun ReservationsListContainer(
    activity: AppCompatActivity,
    allReservations: List<Reservations>?,
    uiState: ReservationUiState,
    viewModel: LoanViewModel
) {
    Log.e("TEST", "uiState.name: "+uiState.name)
    Log.e("TEST", "uiState.phoneNumber: "+uiState.phoneNumber)
    Log.e("TEST", "uiState.event: "+uiState.event)
    Log.e("TEST", "uiState.date: "+uiState.date)
    Log.e("TEST", "uiState.time: "+uiState.time)
    Log.e("TEST", "uiState.notificationText: "+uiState.notificationText)
    Log.e("TEST", "uiState.notificationTime: "+uiState.notificationTime)

    Column(modifier = Modifier.navigationBarsPadding()) {
        Column {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    allReservations?.forEach { item ->
                        Log.e("TEST", "item.name: "+item.name)
                        Log.e("TEST", "Titem.phoneNumber: "+item.phoneNumber)
                        Log.e("TEST", "item.event: "+item.event)
                        Log.e("TEST", "item.date: "+item.date)
                        Log.e("TEST", "item.time: "+item.time)
                        Log.e("TEST", "item.notificationText: "+item.notificationText)
                        Log.e("TEST", "item.notificationTime: "+item.notificationTime)
                        ReservationElementView(
                            eventName = item.event,
                            name = item.name,
                            number = item.phoneNumber,
                            date = item.date,
                            onCardClick = {
                                viewModel.selectedReservation(
                                    uiState.copy(
                                        item.id,
                                        item.name,
                                        item.phoneNumber,
                                        item.event,
                                        item.date
                                    )
                                )
                                openReservationActivity(activity, viewModel)
                            },
                            onDeleteClick = {
                                viewModel.deleteReservation(item.name)
                            })
                    }
                }
            }
        }
    }
}


@Composable
fun NavigationGraph(
    activity: AppCompatActivity,
    viewModel: LoanViewModel,
    uiState: ReservationUiState,
    navController: NavHostController,
    allReservations: List<Reservations>?
) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home.screen_route,
    ) {
        composable(BottomNavItem.Home.screen_route) {
            ReservationsListContainer(
                activity = activity,
                allReservations = allReservations,
                uiState = uiState,
                viewModel = viewModel
            )
        }
        composable(BottomNavItem.MyNetwork.screen_route) {

            EmptyScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            AddReservationScreen(viewModel, navController, uiState)
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
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
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

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MeetimeApp_v3Theme {
        NavigationGraph(
            activity = AppCompatActivity(),
            viewModel = LoanViewModel(ReservationsInteractor(FakeReservationsRepository())),
            uiState = ReservationUiState(
                id = 0L,
                name = "Eduard",
                phoneNumber = "67678686",
                event = "Birthday",
                date = "15.07.1992",
                time = "21:36",
                notificationText = "You have been born at ",
                notificationTime = "21:36"
                ),
            navController = NavHostController(AppCompatActivity()),
            allReservations = listOf()
        )
    }
}

private fun openReservationActivity(activity: AppCompatActivity, viewModel: LoanViewModel) {
    val reservationBottomSheet = ReservationEditBottomSheet(viewModel)
    reservationBottomSheet.show(activity.supportFragmentManager, "RESERVATION_EDIT_BOTTOM_SHEET")
}


