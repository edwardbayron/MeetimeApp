package com.paybrother.main.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReservationElementView(title: String, sum: Int, date: String, onCardClick: () -> Unit, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
            .shadow(4.dp)
            .height(60.dp)
            .background(color = Color.White)
            .clickable {
                onCardClick()
            }

    ) {
        Text(title, fontSize = 8.sp)
        Text(sum.toString(), color = Color.Red)
        Row {
            Text("Loan taken: ", fontSize = 8.sp)
            Text(date.toString(), fontSize = 8.sp)
        }

        Card {
            // TODO progress bar with completed paid loan, add also percentage
        }

        Box(Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                onClick = {
                    onDeleteClick()
                }) {
                Icon(Icons.Filled.Delete, null)
            }
        }


    }

}


@Preview
@Composable
fun Preview(){
    //LoanElementView("Brother", 1000, Date(), onCardClick = {})
    ReservationElementView("Brother", 1000, "Date", onCardClick = {}, onDeleteClick = {})
}