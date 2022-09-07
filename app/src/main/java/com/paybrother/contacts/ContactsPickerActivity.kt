package com.paybrother.contacts

import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.paybrother.R
import com.paybrother.databinding.ActivityContactsPickerBinding
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contacts_picker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsPickerActivity : FragmentActivity(){


    private var cursorAdapter: SimpleCursorAdapter? = null
    lateinit var contactsPickerAdapter: ContactsPickerAdapter
    private val viewModel: ContactsPickerViewModel by viewModels()
    lateinit var binding: ActivityContactsPickerBinding

    val loadedContacts = mutableListOf<ContactItem>()

    val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
        override fun onItemClicked(item: ContactItem) {

        }

    }

    private var roomDb : ReservationDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDb = ReservationDatabase.getInstance(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_picker)

        contactsPickerAdapter = ContactsPickerAdapter(loadedContacts, contactsPickerCallback)

        contacts_picker_upload_btn?.setOnClickListener {
            viewModel.initContactsUploading(this)
        }

        CoroutineScope(Dispatchers.IO).launch {

            val list = roomDb?.contactsDao()?.getcontactList()

            for(i in list!!){
                loadedContacts.add(ContactItem(i.displayName, i.phoneNumber))
            }
        }


        contacts_picker_rv?.adapter = contactsPickerAdapter
        contacts_picker_rv.layoutManager = LinearLayoutManager(this)


        setupDataListeners()
    }

    private fun setupDataListeners(){
        viewModel.contactsList.observe(this) {
            contactsPickerAdapter.setData(it)
        }

    }

}