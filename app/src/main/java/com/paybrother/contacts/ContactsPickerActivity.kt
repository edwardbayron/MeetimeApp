package com.paybrother.contacts

import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.paybrother.R
import com.paybrother.databinding.ActivityContactsPickerBinding
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contacts_picker.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsPickerActivity : FragmentActivity(){


    lateinit var contactsPickerAdapter: ContactsPickerAdapter
    private val viewModel: ContactsPickerViewModel by viewModels()
    lateinit var binding: ActivityContactsPickerBinding

    private var loadedContacts = mutableListOf<ContactItem>()
    private var checkboxTestList = mutableListOf<ContactItem>()

    val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
        override fun onItemClicked(item: ContactItem) {

        }

        override fun onContactChecked(item: ContactItem) {
            Logger.wtf("TEST: item: "+item.firstName)
            Logger.wtf("TEST: item: "+item.lastName)
            checkboxTestList.add(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_picker)

        setupDataListeners()

        contacts_picker_upload_btn?.setOnClickListener {
            viewModel.initContactsUploading(this)
        }

        contacts_upload_accept?.setOnClickListener {
            if(checkboxTestList.isNotEmpty()) {
                viewModel.sendSelectedContacts(checkboxTestList)
                supportFragmentManager?.beginTransaction()
                    ?.add(R.id.contacts_picker_root_container, ContactsFragmentV2())?.commit()
            }
            else{
                Toast.makeText(this, "List is empty, nothing to upload", 1000).show()
            }
        }

        contactsPickerAdapter = ContactsPickerAdapter(loadedContacts, contactsPickerCallback)
        contacts_picker_rv?.adapter = contactsPickerAdapter
        contacts_picker_rv.layoutManager = LinearLayoutManager(this)



    }

    private fun setupDataListeners(){
        viewModel.contactsList.observe(this) {
            contactsPickerAdapter.setData(it)
            Logger.wtf("YOLO: "+it)
        }

        viewModel.databaseContactsList.observe(this){
            loadedContacts = it
        }

    }

}