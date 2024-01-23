package com.paybrother.main.app.compose

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.paybrother.R
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.fake.FakeReservationsRepository
import com.paybrother.main.app.repository.ReservationsInteractor
import com.paybrother.main.app.viewmodels.LoanViewModel
import java.util.Calendar

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
            Column(modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
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
                //gfsagsfghfsagdfs
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

                DatePickerContent()

                TimePickerContent()

                TextNotificationContent()

                TextNotificationScheduleContent()

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

@Composable
fun DatePickerContent(){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    // Disable past dates
    datePicker.datePicker.minDate = calendar.timeInMillis

    Text(
        text = if (selectedDateText.isNotEmpty()) {
            "Selected date is $selectedDateText"
        } else {
            "Please pick a date"
        }
    )

    Button(
        onClick = {
            datePicker.show()
        }
    ) {
        Text(text = "Select a date")
    }
}

@Composable
fun TimePickerContent(){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedTimeText by remember { mutableStateOf("") }

// Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )

    Text(
        text = if (selectedTimeText.isNotEmpty()) {
            "Selected time is $selectedTimeText"
        } else {
            "Please select the time"
        }
    )

    Button(
        onClick = {
            timePicker.show()
        }
    ) {
        Text(text = "Select time")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNotificationContent(){
    val reservationNotifyText = remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = colorResource(id = R.color.black)
                ),
                shape = RoundedCornerShape(10)
            ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = "Notification text") },
        value = reservationNotifyText.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            reservationNotifyText.value = it
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNotificationScheduleContent(){
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("")}

    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {


        // TODO do it like 1 to 24h, 1 day to 6 days, 1 to 3 weeks and month
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { newValue -> expanded = newValue}
        ) {

            TextField(
                value = time,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                placeholder = {
                    Text(text = "Please select time")
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                for(time in setupNotificationTime()) {
                    DropdownMenuItem(
                        text = {
                            Text(time)
                        },
                        onClick = {
                            expanded = false
                        }
                    )
                }
            }



        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddNewReservationContent(){
    AddNewReservationContent(
        viewModel = LoanViewModel(ReservationsInteractor(FakeReservationsRepository())),
        navController = NavController(AppCompatActivity())
    )
}

private fun setupNotificationTime(): MutableList<String> {
    val hoursAndWeeksList = mutableListOf<String>()

    val min15 = "15 minutes"
    val min30 = "30 minutes"
    val hour1 = "1 hour"
    val hour2 = "2 hours"
    val hour3 = "3 hours"
    val day1 = "1 day"
    val day2 = "2 days"
    val week = "1 week"

    hoursAndWeeksList.add(min15)
    hoursAndWeeksList.add(min30)
    hoursAndWeeksList.add(hour1)
    hoursAndWeeksList.add(hour2)
    hoursAndWeeksList.add(hour3)
    hoursAndWeeksList.add(day1)
    hoursAndWeeksList.add(day2)
    hoursAndWeeksList.add(week)

    return hoursAndWeeksList
}