package com.paybrother.main.app.compose.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paybrother.main.app.data.ContactData

@Composable
fun ContactSelectedItemUi(contact: ContactData, onContactSelected: () -> Unit) {
    val selectedItem = remember { mutableStateOf(false) }



    Box(modifier = Modifier.padding(top = 5.dp, start = 8.dp, end = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = MaterialTheme.colors.surface)
                .shadow(2.dp)
                .padding(start = 6.dp, end = 12.dp)
        ) {

            Text(
                text = contact.contactName,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 12.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        Checkbox(
            modifier = Modifier.align(Alignment.CenterEnd),
            checked = selectedItem.value,
            onCheckedChange = {
                onContactSelected()
                check(true)
                selectedItem.value = it
            })
    }

}

@Preview
@Composable
fun ContactSelectedItemUiPreview() {
    ContactSelectedItemUi(ContactData("Alok Ojha", "58053317"), {})
}
