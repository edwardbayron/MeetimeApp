package com.paybrother.main.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.paybrother.R
import com.paybrother.main.app.compose.contacts.ContactItemUI
import com.paybrother.main.app.data.ContactData

@Composable
fun ContactScreenContent(){

    val listItemContact = remember { mutableStateOf(arrayListOf(
        ContactData("Andrei", "58053317"),
        ContactData("Georgi", "55631714")
    )) }


    Box (modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.white))
        ) {
            listItemContact.value.forEach { item ->
                ContactItemUI(item)
            }

        }
    }


}

@Preview
@Composable
fun ContactScreenContentPreview(){
    ContactScreenContent()
}