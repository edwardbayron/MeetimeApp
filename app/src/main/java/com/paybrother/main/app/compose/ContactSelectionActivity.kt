package com.paybrother.main.app.compose

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paybrother.main.app.compose.contacts.ContactSelectedItemUi
import com.paybrother.main.app.data.ContactData
import com.paybrother.main.app.data.ContactItem
import com.paybrother.main.app.viewmodels.ContactSelectionViewModel
import com.paybrother.main.app.viewmodels.ContactViewModelFactory
import com.paybrother.ui.theme.MeetimeApp_v3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsSelectionActivity : ComponentActivity() {

    //private val viewModel: ContactsPickerViewModel by viewModels()

    private var loadedContacts = mutableListOf<ContactItem>()
    private var checkboxTestList = mutableListOf<ContactItem>()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeetimeApp_v3Theme {
                val owner = LocalViewModelStoreOwner.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold {

                        owner?.let {
                            val viewModel: ContactSelectionViewModel = viewModel(
                                it,
                                "ContactViewModel",
                                ContactViewModelFactory(
                                    LocalContext.current.applicationContext as Application
                                )
                            )
                            ContactsUploadContainer(viewModel, this)
                        }


                    }
                }
            }
        }
    }
}

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

@Composable
fun ContactsUploadContainer(viewModel: ContactSelectionViewModel, activity: ComponentActivity){
    val contactsDownloadedList by viewModel.contactsList.observeAsState(listOf())
    val selectedContacts = remember { mutableListOf<ContactItem>() }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
            Toast.makeText(activity, "Denied", LENGTH_LONG).show()
        }
    }

    Column {
        ContactsAppBarView()
        Column {
            contactsDownloadedList.forEach {item ->
                ContactSelectedItemUi(
                    contact = ContactData(
                        contactName = item.firstName,
                        contactPhoneNumber = item.lastName),
                    onContactSelected = {
                        selectedContacts.add(item)
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Row (modifier = Modifier.align(Alignment.BottomCenter)){
                Button(
                    onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                activity,
                                Manifest.permission.READ_CONTACTS
                            ) -> {
                                // Some works that require permission
                                viewModel.initContactsUploading(activity)
                                Log.d("ExampleScreen","Code requires permission")
                            }
                            else -> {
                                // Asking for permission
                                launcher.launch(Manifest.permission.READ_CONTACTS)
                            }
                        }
                    }) {
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
                Icon(Icons.Filled.ArrowBack, null)
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
    val context = LocalContext.current
    ContactsUploadContainer(ContactSelectionViewModel(context.applicationContext as Application), ComponentActivity())
}