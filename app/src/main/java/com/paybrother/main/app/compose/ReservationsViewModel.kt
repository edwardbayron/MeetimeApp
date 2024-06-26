package com.paybrother.main.app.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue

@Composable
fun ReservationElementView(
    eventName: String,
    name: String,
    number: String,
    date: String,
    time: String,
    notificationText: String,
    notificationTime: String,
    onCardClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    var arrowExpandableIcon by remember { mutableStateOf(Icons.Filled.ArrowDropDown)}
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
            .shadow(4.dp)
            .background(color = MaterialTheme.colors.surface)
            .clickable {
                if(!visible){
                    visible = true
                    arrowExpandableIcon = Icons.Filled.ArrowDropUp
                }

                else if(visible){
                    visible = false
                    arrowExpandableIcon = Icons.Filled.ArrowDropDown
                }
            }

    ) {


        Text(modifier = Modifier.padding(10.dp), text = eventName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Row {
            Text(modifier = Modifier.padding(10.dp), text = "Date: $date", fontSize = 14.sp, color = Color.Black)
        }
        Text(modifier = Modifier.padding(10.dp), text = "Time: $time", fontSize = 14.sp, color = Color.Black)


        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        ) {
            Column {
                Text(modifier = Modifier.padding(10.dp), text = name, fontSize = 14.sp)
                Text(modifier = Modifier.padding(10.dp), text = number, color = Color.Red, fontSize = 14.sp)
                Text(modifier = Modifier.padding(10.dp), text = "Notification message: $notificationText", fontSize = 14.sp, color = Color.Black)
                Text(modifier = Modifier.padding(10.dp), text = "Notify before: $notificationTime", fontSize = 14.sp, color = Color.Black)

                Row(modifier = Modifier.fillMaxWidth()){
                    Box {
                        IconButton(
                            onClick = {
                                onCardClick()
                            }) {
                            Icon(Icons.Filled.Edit, null)
                        }
                    }

                    Box {
                        IconButton(
                            onClick = {
                                onDeleteClick()
                            }) {
                            Icon(Icons.Filled.Delete, null)
                        }
                    }
                }
            }
        }

        Box(Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                onClick = {
                    if(visible && arrowExpandableIcon == Icons.Filled.ArrowDropUp){
                        visible = false
                        arrowExpandableIcon = Icons.Filled.ArrowDropDown
                    }
                    else{
                        visible = true
                        arrowExpandableIcon = Icons.Filled.ArrowDropUp
                    }
                }) {
                Icon(arrowExpandableIcon, null)
            }
        }
    }

}

@Preview
@Composable
fun Preview(){
    ReservationElementView(
        eventName = "Nails + Massage",
        name = "Aleksei Borovikov",
        number = "55665544",
        date = "2010-05-30",
        time = "22:15",
        notificationText = "You have an appointment at ",
        notificationTime = "2 hours",
        onCardClick = {},
        onDeleteClick = {}
    )
}