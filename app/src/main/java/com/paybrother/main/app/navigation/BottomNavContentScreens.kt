package com.paybrother.main.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.paybrother.R
import com.paybrother.main.app.compose.AddNewReservationContent
import com.paybrother.main.app.compose.ContactScreenContent
import com.paybrother.main.app.viewmodels.LoanViewModel

object BottomNavContentScreens {

    @Composable
    fun EmptyScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .wrapContentSize(Alignment.Center)

        ) {
            Text(
                text = "Empty Screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }

    @Composable
    fun AddReservationScreen(viewModel: LoanViewModel, navController: NavHostController) {
        AddNewReservationContent(viewModel, navController)
    }


    @Composable
    fun NotificationScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .navigationBarsPadding()
                .wrapContentSize(Alignment.Center)
        ) {

            Text(
                text = "Notification Screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }


    @Composable
    fun ContactsScreen() {
        ContactScreenContent()
    }

}