package com.paybrother.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paybrother.data.LoanParcelable
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import java.util.*

class ReservationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = this.intent.extras?.getSerializable("reservationData") as LoanParcelable
            MeetimeApp_v3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservationContainer(
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
fun ReservationContainer(data: LoanParcelable, onBackPress: () -> Unit) {
    Column {
        AppBarView(onBackPress)
        ReservationDataContainer(data)


    }
}

@Composable
fun AppBarView(onBackPress: () -> Unit){
    val mContext = LocalContext.current
    var mDisplayMenu by remember { mutableStateOf(false) }

    androidx.compose.material.TopAppBar(
        //elevation = 4.dp,
        title = {
            Text("Reservation Details")
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPress()
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }, actions = {
            IconButton(onClick = {
                mDisplayMenu = !mDisplayMenu
            }) {
                Icon(Icons.Filled.MoreVert, null)
            }

            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {

                DropdownMenuItem(text = {
                    Text(text = "Edit")
                }, onClick = {
                    Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT).show()
                })

                DropdownMenuItem(text = {
                    Text(text = "Delete")
                }, onClick = {
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show()
                })


            }


        })
}


@Composable
fun ReservationDataContainer(data: LoanParcelable){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {

        Box(Modifier.fillMaxSize()){
            Column {
                Text(text = data.title)
                Text(text = data.sum.toString())
                Text(text = data.date.toString())
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = {
                    //OnClick Method
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

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MeetimeApp_v3Theme {
        ReservationContainer(LoanParcelable(", ", "", 0, Date()), {})
    }
}
