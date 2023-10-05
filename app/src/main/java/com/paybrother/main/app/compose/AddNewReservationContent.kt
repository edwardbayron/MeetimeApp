package com.paybrother.main.app.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.paybrother.R
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewReservationContent(viewModel: LoanViewModel, navController: NavController) {
    val txtFieldError = remember { mutableStateOf("") }
    val reservationName = remember { mutableStateOf("") }
    val reservationPhoneNumber = remember { mutableStateOf("") }
    val reservationEvent = remember { mutableStateOf("") }
    val reservationDate = remember { mutableStateOf("") }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Add reservation",
                    style = TextStyle(
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
                                color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.red)
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
                    })

                TextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.red)
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
                    })
                //test commit
                TextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.red)
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
                    })

                TextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.black else R.color.red)
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
                    })

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if(reservationName.value.isNotEmpty() && reservationPhoneNumber.value.isNotEmpty() && reservationEvent.value.isNotEmpty()) {
                            viewModel.insertReservation(ReservationUiState(0L, reservationName.value, reservationPhoneNumber.value, reservationEvent.value, reservationDate.value))
                            navController.navigate("home")
                        }
                        else{
                            txtFieldError.value = "All fields must be filled"
                        }
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