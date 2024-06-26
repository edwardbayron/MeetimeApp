package com.paybrother.main.app.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paybrother.main.app.data.ReservationUiState

@Composable
fun ReservationDataScreen(uiState: ReservationUiState, onBackPress: () -> Unit) {
    ReservationDataContainer(uiState)
    EditAppBarView(uiState, onBackPress)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDataContainer(uiState: ReservationUiState) {
    //var reservationUiState = remember { mutableStateOf(data) }
//    var reservationTitle = remember { mutableStateOf(data.title) }
//    var reservationSum = remember { mutableStateOf(data.sum) }
//    var reservationDate = remember { mutableStateOf(data.date) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        Box(Modifier.fillMaxSize()) {
            Column {
                Text(text = uiState.name)
                Text(text = uiState.phoneNumber)
                Text(text = uiState.event)
                Text(text = uiState.date)

                OutlinedTextField(
                    value = uiState.name,
                    enabled = false,
                    onValueChange = {
                        uiState.name = it
                    },
                    label = { Text(uiState.name)}
                )

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAppBarView(data: ReservationUiState, onBackPress: () -> Unit) {
    var editEnabled by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text("Edit Reservation")
        },
        navigationIcon = {
            IconButton(onClick = {
                editEnabled = false
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        })
}

@Preview(showBackground = true)
@Composable
fun PreviewReservationDataScreen(){
    ReservationDataContainer(ReservationUiState(
        id = 1234567890L,
        name = "test",
        phoneNumber = "test",
        event = "test",
        date = "1992-07-15",
        time = "18:00",
        notificationText = "test",
        notificationTime = "18:00"
        ))
}
