package com.paybrother.main.app.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationEditBottomSheet(
    viewModel: LoanViewModel,
    uiState: ReservationUiState
) {
    val reservationUiState by remember { mutableStateOf(ReservationUiStateItem(
        id = uiState.id,
        name = uiState.name,
        phoneNumber = uiState.phoneNumber,
        event = uiState.event,
        date = uiState.date,
        time = uiState.time,
        notificationText = uiState.notificationText,
        notificationTime = uiState.notificationTime
    )) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {

                Box(Modifier.fillMaxSize()) {
                    Column {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = reservationUiState.name.value,
                            onValueChange = {
                                reservationUiState.name.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.White
                            )
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = reservationUiState.phoneNumber.value,
                            onValueChange = {
                                reservationUiState.phoneNumber.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.White
                            )
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = reservationUiState.event.value,
                            onValueChange = {
                                reservationUiState.event.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.White
                            )
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = reservationUiState.date.value,
                            onValueChange = {
                                reservationUiState.date.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.White
                            )
                        )

                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.updateReservation(
                                    state = ReservationUiState(
                                        id = reservationUiState.id.value,
                                        name = reservationUiState.name.value,
                                        phoneNumber = reservationUiState.phoneNumber.value,
                                        event = reservationUiState.event.value,
                                        date = reservationUiState.date.value,
                                        time = reservationUiState.time.value,
                                        notificationText = reservationUiState.notificationText.value,
                                        notificationTime = reservationUiState.notificationTime.value
                                    )
                                )
                                viewModel.selectedReservation(
                                    uiState.copy(
                                        id = reservationUiState.id.value,
                                        name = reservationUiState.name.value,
                                        phoneNumber = reservationUiState.phoneNumber.value,
                                        event = reservationUiState.event.value,
                                        date = reservationUiState.date.value
                                    )
                                )
                            }) {

                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}




