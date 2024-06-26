package com.paybrother.main.app.compose

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paybrother.R
import com.paybrother.main.app.compose.contacts.ContactItemUI
import com.paybrother.main.app.data.ContactData

@Composable
fun ContactScreenContent(){
    val context = LocalContext.current

    val listItemContact = remember { mutableStateOf(arrayListOf(
        ContactData("Andrei", "58053317"),
        ContactData("Georgi", "55631714")
    )) }


    Surface(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        Box (modifier = Modifier.fillMaxSize()){

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(id = R.color.white))
            ) {
                listItemContact.value.forEach { item ->
                    ContactItemUI(item, {})
                }

            }
        }

        Box(modifier = Modifier.fillMaxSize(0.5f)) {
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 70.dp),
                onClick = {
                    context.startActivity(Intent(context, ContactsSelectionActivity::class.java))
                }) {
                    Text(text = "Add contacts")

            }
        }
    }
}

@Preview
@Composable
fun ContactScreenContentPreview(){
    ContactScreenContent()
}