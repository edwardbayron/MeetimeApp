package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paybrother.old.contacts.ContactItem
import com.paybrother.ui.theme.MeetimeApp_v3Theme

class ContactsPickerActivity : ComponentActivity(){

    //private val viewModel: ContactsPickerViewModel by viewModels()

    private var loadedContacts = mutableListOf<ContactItem>()
    private var checkboxTestList = mutableListOf<ContactItem>()

//    private val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
//        override fun onItemClicked(item: ContactItem) {
//            item.id?.let {
//                viewModel.deleteContact(item.id)
//            }
//
//        }
//
//        override fun onContactChecked(item: ContactItem) {
//            checkboxTestList.add(item)
//        }
//
//    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeetimeApp_v3Theme {
                ContactsUploadContainer()
            }
        }

    }

    fun oldOnCreate(){
//        setupDataListeners()

//        contacts_picker_upload_btn?.setOnClickListener {
//            viewModel.initContactsUploading(this)
//        }
//
//        contacts_upload_accept?.setOnClickListener {
//            if(checkboxTestList.isNotEmpty()) {
//                viewModel.sendSelectedContacts(checkboxTestList)
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.contacts_picker_root_container, ContactsFragmentV2()).commit()
//            }
//            else{
//                Toast.makeText(this, "List is empty, nothing to upload", 1000).show()
//            }
//        }
    }


//    private fun setupDataListeners(){
//        viewModel.contactsList.observe(this) {
//            contactsPickerAdapter.setData(it)
//        }
//
//        viewModel.databaseContacts.observe(this){
//            if(it.isNotEmpty()) {
//                loadedContacts = it
//                contactsPickerAdapter.setData(it)
//                contacts_picker_upload_btn.visibility = View.GONE
//                contacts_upload_accept.visibility = View.GONE
//            }
//            else{
//                contacts_picker_upload_btn.visibility = View.VISIBLE
//                contacts_upload_accept.visibility = View.VISIBLE
//
//            }
//        }
//
//    }

}

@Composable
fun ContactsUploadContainer(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column {
            ContactsAppBarView()

            Box(modifier = Modifier.fillMaxSize()) {
                Row (modifier = Modifier.align(Alignment.BottomCenter)){
                    Button(
                        onClick = { /*TODO*/ }) {
                        Text(text = "Upload contacts")
                    }

                    Button(
                        onClick = { /*TODO*/ }) {
                        Text(text = "Accept contacts")
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsAppBarView() {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text("Upload contacts")
        },
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Filled.Menu, null)
            }
        }, actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Check, null)
            }
        })
}

@Preview
@Composable
fun PreviewContactSelectionActivity(){
    ContactsUploadContainer()
}