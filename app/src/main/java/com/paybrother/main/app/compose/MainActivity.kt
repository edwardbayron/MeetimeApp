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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigation(navController = navController)
                        }
                    ) {

                        NavigationGraph(
                            activity = this@MainActivity,
                            viewModel = viewModel,
                            uiState = uiState,
                            navController = navController,
                            listReservations
                        )
                    }
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
    Column(modifier = Modifier.navigationBarsPadding()) {
        AppBarView()
        Column {
            Box(modifier = Modifier.fillMaxSize()) {
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
//                FloatinActionButton(uiState) {
//                    viewModel.insertReservation()
//                }
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
            AddReservationScreen(viewModel, navController)
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

//@Composable
//fun FloatinActionButton(uiState: ReservationUiState, insertReservation: () -> Unit) {
//    val addReservation = remember { mutableStateOf(false) }
//
//    Box(Modifier.fillMaxSize()) {
//        FloatingActionButton(
//            modifier = Modifier
//                .align(alignment = Alignment.BottomEnd)
//                .padding(bottom = 70.dp, end = 10.dp),
//            onClick = {
//                addReservation.value = true
//            },
//            containerColor = Color.Red,
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = "Add FAB",
//                tint = Color.White,
//            )
//        }
//    }
//
//    if (addReservation.value) {
//        AddReservationDialog(uiState = uiState, insertReservation)
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReservationDialog(uiState: ReservationUiState, insertReservation: () -> Unit) {


}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MeetimeApp_v3Theme {
        NavigationGraph(
            activity = AppCompatActivity(),
            viewModel = LoanViewModel(ReservationsInteractor(FakeReservationsRepository())),
            uiState = ReservationUiState(1, ", ", "", "", ""),
            navController = NavHostController(AppCompatActivity()),
            allReservations = listOf()
        )
    }
}

private fun openReservationActivity(activity: AppCompatActivity, viewModel: LoanViewModel) {
    val reservationBottomSheet = ReservationEditBottomSheet(viewModel)
    reservationBottomSheet.show(activity.supportFragmentManager, "RESERVATION_EDIT_BOTTOM_SHEET")
}