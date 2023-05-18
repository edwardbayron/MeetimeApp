package com.paybrother.main.app.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paybrother.main.app.data.ReservationUiState

@Composable
fun ReservationDataScreen(data: ReservationUiState, onBackPress: () -> Unit) {
    ReservationDataContainer(data)
    EditAppBarView(data, onBackPress)
}

@Composable
fun ReservationDataContainer(data: ReservationUiState) {
    val reservationTitle = rememberSaveable { mutableStateOf(data.title) }
    val reservationSum = rememberSaveable { mutableStateOf(data.sum) }
    val reservationDate = rememberSaveable { mutableStateOf(data.date) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        Box(Modifier.fillMaxSize()) {
            Column {
                Text(text = data.id.toString())
                Text(text = reservationTitle.value)
                Text(text = reservationSum.value)
                Text(text = reservationDate.value)
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
    ReservationDataContainer(ReservationUiState(1234567890L,"test", "test", "test"))
}
