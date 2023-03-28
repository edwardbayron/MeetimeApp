package com.paybrother.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paybrother.data.LoanParcelable
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.util.*

class ReservationEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = this.intent.extras?.getSerializable("reservationData") as LoanParcelable
            MeetimeApp_v3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservationEditContainer(
                        data,
                        onBackPress = {
                            onBackPressed()
                        }
                    )
                }
            }
        }
    }


}

@Composable
fun ReservationEditContainer(data: LoanParcelable, onBackPress: () -> Unit) {
    Column {
        AppBarEditView(onBackPress)
        ReservationEditDataContainer(data)


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarEditView(onBackPress: () -> Unit){
    val mContext = LocalContext.current
    var mDisplayMenu by remember { mutableStateOf(false) }

    androidx.compose.material.TopAppBar(
        //elevation = 4.dp,
        title = {
            Text("Edit reservation",
                maxLines = 1,
                color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPress()
            }) {
                Icon(
                    Icons.Filled.ArrowBack, null,
                    tint = Color.White
                )
            }
        }, actions = {
            IconButton(
                modifier = Modifier.width(50.dp),
                onClick = {
                mDisplayMenu = !mDisplayMenu
            }) {
                Text(
                    text = "Save",
                    maxLines = 1,
                    color = Color.White
                )
            }


        })
}

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReservationEditDataContainer(data: LoanParcelable){

    var reservationTitleText by remember { mutableStateOf(data.title) }
    var reservationSumText by remember { mutableStateOf(data.sum.toString()) }
    var reservationDateText by remember { mutableStateOf(data.date.toString())}

    val gradientColors = listOf(Cyan, Color.Red, Color.Blue, Color.Yellow /*...*/)

    val brush = remember {
        Brush.linearGradient(
            colors = gradientColors
        )
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {

        Box(Modifier.fillMaxSize()){
            Column {
                TextField(
                    value = reservationTitleText,
                    onValueChange = { reservationTitleText = it },
                    textStyle = TextStyle(brush = brush))

                TextField(
                    value = reservationSumText,
                    onValueChange = { reservationSumText = it },
                    textStyle = TextStyle(brush = brush))

                TextField(
                    value = reservationDateText,
                    onValueChange = { reservationDateText = it },
                    textStyle = TextStyle(brush = brush))

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeetimeApp_v3Theme {
        ReservationEditContainer(LoanParcelable(", ", "", 0, Date()), {})
    }
}